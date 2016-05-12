#include "list.h"
#include <time.h>
List *l_new()
{
    List *lst = malloc(sizeof(List));
    lst->first = NULL;
    lst->count = 0;
    return lst;
}

SNode *snode(int value)
{
    SNode *n = malloc(sizeof(SNode));
    n->value = value;
    return n;
}

bool l_isEmpty(List *list)
{
    return list->first == NULL;
}

bool l_find(List *list, int value)
{
    SNode *cur = list->first;
    while(cur != NULL)
    {
        if(cur->value == value)
            return true;
        cur = cur->next;
    }
    return false;
}
void l_insertStart(List *list, list_val_t value)
{
    l_insert(list, 0, value);
}

void l_insertEnd(List *list, list_val_t value)
{
    l_insert(list, list->count, value);
}

void l_insert(List *list, int index, list_val_t value)
{
    if(index > list->count)
    {
        fprintf(stderr, "List index out of range.\n");
        exit(EXIT_FAILURE);
    }
    SNode *n = snode(value);
    SNode *tmp;
    if(list->first == NULL)
    {
        list->first = n;
        list->count++;
        return;
    }
    if(index == 0)
    {
        n->next = list->first;
        list->first = n;
        list->count++;
        return;
    }

    SNode *cur = list->first;
    for(int i = 1; i < index; i++)
    {
        cur = cur->next;
    }
    tmp = cur->next;
    cur->next = n;
    n->next = tmp;
    list->count++;
}

void l_print(List *list)
{
    SNode *cur = list->first;
    printf("list size: %d.\n", list->count);
    while(cur != NULL)
    {
        printf("%d\n", cur->value);
        cur = cur->next;
    }
    printf("----------------\n");

}

list_val_t l_fetch(List *list, int index)
{
    if(index > list->count)
    {
        fprintf(stderr, "List index out of range.\n");
        exit(EXIT_FAILURE);
    }
    SNode *cur = list->first;
    for(int i = 0; i<index; i++)
    {
        cur = cur->next;
    }
    return cur->value;
}

void l_free(List *list)
{
    SNode *cur = list->first;
    while(cur != NULL)
    {
        list->first = list->first->next;
        free(cur);
        cur = list->first;
    }
    list->first = NULL;
    list->count = 0;
}

void l_remove(List *list, int index)
{
    if(index > list->count)
    {
        fprintf(stderr, "list index out of range\n");
        exit(EXIT_FAILURE);
    }

    SNode *cur = list->first;
    SNode *tmp;
    for(int i = 0; i < index-1; i++)
    {
        cur = cur->next;
    }
    tmp = cur->next;
    cur->next = cur->next->next;
    free(tmp);
    list->count--;
}

// make list1 equal to list1+list2
// list1 cant be null
void l_merge(List *list1, List *list2)
{
    if(list1 == NULL)
    {
        fprintf(stderr, "List1 is null. merge will have no effect.\n");
        return;
    }
    if(list2 == NULL)
        return;

    SNode *last = list1->first;
    while(last->next != NULL)
    {
        last = last->next;
    }
    last->next = list2->first;
    list1->count += list2->count;

}

#define TESTS 1000
#define LIST_SIZE 1000
int main(int argc, char *argv[])
{
    int tests = TESTS;
    int list_size = LIST_SIZE;

    if(argc == 4)
    {
        tests = atoi(argv[1]);
        list_size = atoi(argv[2]);
    }

    srand(time(0));
    double t_rand = 0;
    double t_half = 0;
    double t_first = 0;
    double t_last = 0;
    clock_t start, end;
    double r_avg = 0;

    List *list = l_new();
    for(int i = 0; i < list_size; i++)
    {
        l_insertEnd(list, rand()%10000);
    }

    for(int e = 0; e < tests; e++)
    {
        


        start = clock();
        l_fetch(list, list->count/2);
        end = clock();
        t_half += (double)(end-start) / CLOCKS_PER_SEC;

        start = clock();
        l_fetch(list, 0);
        end = clock();
        t_first += (double)(end - start) / CLOCKS_PER_SEC;

        start = clock();
        l_fetch(list, list->count-1);
        end = clock();
        t_last += (double)(end - start) / CLOCKS_PER_SEC;

        for(int j = 0; j < r_tests; j++)
        {
            int r = rand()%list_size;
            r_avg += r;
            start = clock();
            l_fetch(list, r);
            end = clock();
            t_rand += (double)(end-start) / CLOCKS_PER_SEC;
        }

        l_free(list);
        free(list);
    }
    double total_r_tests = tests*r_tests;
    printf("avg rand(): %f\n", r_avg/total_r_tests);
    printf("[frst] avg: %.10f\n", t_first/(double)tests);
    printf("[last] avg: %.10f\n", t_last/(double)tests);
    printf("[half] avg: %.10f\n", t_half/(double)tests);
    printf("[rand] avg: %.10f\n", t_rand/total_r_tests);


}
