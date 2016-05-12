#include "list.h"
#include <sys/time.h>
#include <time.h>

DList *dl_new()
{
    DList *lst = (DList*) malloc(sizeof(DList));
    lst->first = NULL;
    lst->count = 0;
    return lst;
}

DNode *dnode(list_val_t value)
{
    DNode *n = malloc(sizeof(DNode));
    n->prev = NULL;
    n->next = NULL;
    n->value = value;
    return n;
}

bool dl_isEmpty(DList *list)
{
    return list->first == NULL;
}

bool dl_find(DList *list, list_val_t value)
{
    if(list->first->value== value)  // check first element
        return true;
    DNode *cur = list->first->next; // check the rest
    while(cur != NULL)
    {
        if(cur->value == value)
            return true;
        cur = cur->next;
    }
    return false;

}

void dl_insert(DList *list, int index, list_val_t value)
{
    if(index > list->count)
    {
        fprintf(stderr, "List index out of range.\n");
        exit(EXIT_FAILURE);
    }

    DNode *n = dnode(value);
    if(list->first == NULL)
    {
        n->prev = n;
        n->next = n;
        list->first = n;
        list->count++;
        return;
    }
    if(index == 0)
    {
        n->next = list->first;
        n->prev = list->first->prev;
        list->first->prev->next = n;
        list->first->prev = n;
        list->count++;
        return;
    }
    DNode *cur = list->first;
    for(int i = 1; i < index; i++)
    {
        cur = cur->next;
    }
    n->next = cur->next;
    n->prev = cur;
    cur->next->prev = n;
    cur->next = n;
    list->count++;

}

void dl_print(DList *list)
{
    DNode *cur = list->first;
    if(dl_isEmpty(list)) return;
    do
    {
        printf("%d <- %d -> %d\n", cur->prev->value, cur->value,
                cur->next->value);
        cur = cur->next;
    } while(cur != list->first);
}

void dl_insertStart(DList *list, list_val_t value)
{
    dl_insert(list, 0, value);
}

void dl_insertEnd(DList *list, list_val_t value)
{
    dl_insert(list, list->count, value);
}

void dl_remove(DList *list, int index)
{
    if(index > list->count)
    {
        fprintf(stderr, "list index put of range\n");
        exit(EXIT_FAILURE);
    }

    DNode *cur = list->first;
    for(int i = 0; i < index; i++)
    {
        cur = cur->next;
    }

    cur->prev->next = cur->next;
    cur->next->prev = cur->prev;
    free(cur);
    list->count--;
}

void dl_free(DList *list)
{
    DNode *cur = list->first;
    while(cur->prev != NULL)
    {
        free(cur->prev);
        cur->prev = NULL;
        cur = cur->next;
    }
    list->count = 0;
    list->first = NULL;
    printf("list cleared\n");
}

list_val_t dl_fetch(DList *list, int index)
{
    if(index >= list->count)
    {
        fprintf(stderr, "[fetch] List index (%d) out of range (0..%d).\n",
        index, list->count-1);
        //exit(EXIT_FAILURE);
    }
    DNode *cur = list->first;
    if(index == 0)
    {
    //    printf("feetching 0\n");
        return list->first->value;
    }
    if(index == list->count-1)
    {
    //    printf("fetching last (%d)\n", index);
        return list->first->prev->value;
    }
    if(index > list->count/2)
    {
    //    printf("fetching half(%d)\n", list->count/2);
        for(int i=0; i < list->count-index; i++)
            cur = cur->prev;
    }
    else
    {
        for(int i=0; i<index; i++)
            cur = cur->next;
    }
    return cur->value;
}

double measure_time(DList *list, int index, int verbose);
long measure_time_n(DList *list, int index, int verbose);
void dl_merge(DList *list1, DList *list2)
 {
    DNode *list1f, *list2f, *list1l, *list2l;
    /* keep 'terminal' nodes on both lists */
    list1f = list1->first;
    list2f = list2->first;
    list1l = list1->first->prev;
    list2l = list2->first->prev;

    /* stitch the ends together */
    list1->first->prev->next = list2f;
    list2->first->prev->next = list1f;
    list2->first->prev = list1l;
    list1->first->prev = list2l;
    /* update count */
    list1->count += list2->count;
}
void test_times(DList *list, int tests, int verbose);
#define TESTS 1000
#define LIST_SIZE 1000
int main(int argc, char**argv)
{
    int tests = TESTS;
    int verbose = 0;
    int list_size = LIST_SIZE;
    if(argc == 2)
        verbose = atoi(argv[1]);

    if(argc == 3)
    {
        tests = atoi(argv[1]);
        list_size = atoi(argv[2]);
    }
    if(argc == 4)
    {
        tests = atoi(argv[1]);
        list_size = atoi(argv[2]);
        verbose = atoi(argv[3]);
    }
    printf("ACCESS TIME TEST\n");
    srand(time(0));
    double t_rand = 0;
    double t_half = 0;
    double t_first = 0;
    double t_last = 0;
    int r;
    long r_sum = 0;

    DList *list = dl_new();
    for(int i = 0; i < list_size; i++)
    {
        dl_insertStart(list, rand()%10000);
    }


    /*********************
        access time tests
     *********************/
     test_times(list, tests, verbose);
     exit(0);
     for(int e = 1; e <= tests; e++)
     {
        // first element
        if(verbose)
        {
            printf("Test #%d\n", e);
            printf("\t%-8s ", "[FIRST]");
        }

        t_first += measure_time(list, 0, verbose);
        //t_first += (double)(measure_time(list, 0, verbose) - t_first) / e;

        // last element
        if(verbose)
            printf("\t%-8s ", "[LAST]");
        t_last += measure_time(list, list->count-1, verbose);
        //t_last += (double)(measure_time(list, list->count-1, verbose) - t_last) / e;

        // middle element
        if(verbose)
            printf("\t%-8s ", "[HALF]");
        t_half += measure_time(list, list->count/2, verbose);
        //t_half += (double)(measure_time(list, list->count/2, verbose) - t_half) / e;

        // random element
        r = rand()%list_size;
        r_sum += r;
        if(verbose)
            printf("\t%-8s ", "[RAND]");
        t_rand += measure_time(list, r, verbose);
        //t_rand  += (double)(measure_time(list, r, verbose) - t_rand) / e;
        //printf("test #%d complete\n", e);
    }
    // print results
    printf("%-8s total: %.6f\n", "[FIRST]", t_first);
    printf("%-8s total: %.6f\n", "[LAST]", t_last);
    printf("%-8s total: %.6f\n", "[HALF]", t_half);
    printf("%-8s total: %.6f | avg rand(): %ld\n", "[RAND]",
        t_rand, r_sum/tests);
    if(t_first - t_last > 0.1)
        printf("OVER 1\n");
    else if(t_first - t_last > 0.01)
        printf("OVER 2\n");
    else if(t_first - t_last > 0.001)
        printf("OVER 3\n");
    else if(t_first > t_last)
        printf("OVER +\n");
}

void test_times(DList *list, int tests, int verbose)
{
    int r;
    long r_sum = 0;
    double t_first = 0, t_last = 0, t_half = 0, t_rand = 0;
    for(int e = 1; e <= tests; e++)
    {
        // first element
        if(verbose)
        {
            printf("Test #%d\n", e);
            printf("\t%-8s ", "[FIRST]");
        }

        t_first += measure_time_n(list, 0, verbose);
        //t_first += (double)(measure_time(list, 0, verbose) - t_first) / e;

        // last element
        if(verbose)
            printf("\t%-8s ", "[LAST]");
        t_last += measure_time_n(list, list->count-1, verbose);
        //t_last += (double)(measure_time(list, list->count-1, verbose) - t_last) / e;

        // middle element
        if(verbose)
            printf("\t%-8s ", "[HALF]");
            t_half += measure_time_n(list, list->count/2, verbose);
        //t_half += (double)(measure_time(list, list->count/2, verbose) - t_half) / e;

        // random element
        r = rand()%list->count;
        r_sum += r;
        if(verbose)
            printf("\t%-8s ", "[RAND]");
        t_rand += measure_time_n(list, r, verbose);
        //t_rand  += (double)(measure_time(list, r, verbose) - t_rand) / e;
        //printf("test #%d complete\n", e);
    }
   // print results
    printf("%-8s total: %.6f\n", "[FIRST]", t_first);
    printf("%-8s total: %.6f\n", "[LAST]", t_last);
    printf("%-8s total: %.6f\n", "[HALF]", t_half);
    printf("%-8s total: %.6f | avg rand(): %ld\n", "[RAND]",
        t_rand, r_sum/tests);
    if(t_first - t_last > 10000)
        printf("OVER @ 10000\n");
    else if(t_first - t_last > 1000)
        printf("OVER @ 1000\n");
    else if(t_first - t_last > 100)
        printf("OVER @ 100\n");
    else if(t_first - t_last > 10)
        printf("OVER @ 10\n");
}

double measure_time(DList *list, int index, int verbose)
{
    //struct timeval start, end;
    clock_t start, end;
    //gettimeofday(&start, NULL);
    start = clock();
    dl_fetch(list, index);
    end = clock();
    //gettimeofday(&end, NULL);
    if(verbose)
    {
        printf("fetch %4d, start: %8ld, end: %8ld, time: %2ld\n",
            index, start, end, end - start);
        //printf("fetch %4d, start: %8ld, end: %8ld, time: %2ld\n",
        //  index, start.tv_usec, end.tv_usec, end.tv_usec - start.tv_usec);
    }
    //return end.tv_usec - start.tv_usec;
    return (double)(end - start);
}

long measure_time_n(DList *list, int index, int verbose)
{
    struct timespec start, stop;
    clockid_t clk = CLOCK_REALTIME;
    clock_gettime(clk, &start);
    dl_fetch(list, index);
    clock_gettime(clk, &stop);
    long delta = stop.tv_nsec - start.tv_nsec;
    if(verbose)
    {
        printf("fetch %4d, start: %8ld.%8ld, end: %8ld.%8ld, time: %2ld\n",
            index, start.tv_sec, start.tv_nsec,
            stop.tv_sec, stop.tv_nsec, delta);
    }
    if(delta < 0)
    {
        printf("DELTA\n");
        return -delta;
    }
    return delta;
}
