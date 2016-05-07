#include "util.h"
#include "sort.h"
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#define N_SORTS 8
// generators
long *generate_list(OrderingE type, long size, long max, int logging);
long *generate_list_random(long size, long max);
long *generate_list_ascending(long size);
long *generate_list_descending(long size);

struct flags {
    int insert;
    int merge;
    int quick;
    int qi;
    int mi;
    int yaro;
};

struct sort {
    char enabled;
    char *filename;
};

// main test method
void test(long test_size, int logging, OrderingE list_type, int qi_threshold,
    int qm_threshold, int radix, long max, struct sort conf[]);



// launcher
int main(int argc, char **argv)
{
    // DEFAULT SETTINGS
    // length of test list
    long test_size = 30;
    // how many details are logged
    int logging = 0;
    // array length at which quick_insert switches from quick to insert sort
    int qi_threshold = 30;
    // array length at which merge_insert switches from merge to insert sort
    int mi_threshold = 30;
    // base used by radix sort
    int radix = 10;
    // type of list to generate
    OrderingE list_type = Random;
    struct sort conf[N_SORTS];
    for(int i = 0; i < N_SORTS; i++)
    {
        conf[i].enabled = 1;
        conf[i].filename = NULL;
    }
    char *basename;
    int base_length;

    // parse command line options
    extern int opterr;
    opterr = 0;
    extern char *optarg;
    int c;
    do
    {
        c = getopt(argc,argv,"i:m:o:r:s:t:v:fABCDEFGH");
        switch (c)
        {
            case 's':
                test_size = strtol(optarg, NULL, 10);
                break;
            case 't':
                if (strcmp(optarg, "asc") == 0
                    || strcmp(optarg, "ascending") == 0)
                    {
                        list_type = Ascending;
                    }
                else if (strcmp(optarg, "desc") == 0
                         || strcmp(optarg, "descending") == 0)
                     {
                         list_type = Descending;
                     }
                else
                {
                    list_type = Random;
                }
                break;
            case 'v':
                logging = (int)strtol(optarg, NULL, 10);
                break;
            case 'i':
                qi_threshold = (int)strtol(optarg, NULL, 10);
                break;
            case 'm':
                mi_threshold = (int)strtol(optarg, NULL, 10);
                break;
            case 'r':
                radix = (int)strtol(optarg, NULL, 10);
                break;
            case 'f':
                for(int i = 0; i < N_SORTS; i++) {
                    conf[i].enabled = 0;
                }
                break;
            case 'A':
                conf[0].enabled=1;
                break;
            case 'B':
                conf[1].enabled=1;
                break;
            case 'C':
                conf[2].enabled=1;
                break;
            case 'D':
                conf[3].enabled=1;
                break;
            case 'E':
                conf[4].enabled=1;
                break;
            case 'F':
                conf[5].enabled=1;
                break;
            case 'G':
                conf[6].enabled=1;
                break;
            case 'H':
                conf[7].enabled=1;
                break;
            case 'o':
                base_length = (strlen(optarg) + 1);
                basename = malloc(base_length * sizeof(char));
                strcpy(basename, optarg);
                // basename.insert
                conf[0].filename = malloc(base_length+6+1);
                sprintf(conf[0].filename,"%s.%s",basename,"insert");
                // basename.merge
                conf[1].filename = malloc(base_length+5+1);
                sprintf(conf[1].filename,"%s.%s",basename,"merge");
                // basename.quick
                conf[2].filename = malloc(base_length+5+1);
                sprintf(conf[2].filename,"%s.%s",basename,"quick");
                // basename.quickins
                conf[3].filename = malloc(base_length+8+1);
                sprintf(conf[3].filename,"%s.%s",basename,"quickins");
                // basename.mergeins
                conf[4].filename = malloc(base_length+8+1);
                sprintf(conf[4].filename,"%s.%s",basename,"mergeins");
                // basename.yaro
                conf[5].filename = malloc(base_length+4+1);
                sprintf(conf[5].filename,"%s.%s",basename,"yaro");
                // basename.count
                conf[6].filename = malloc(base_length+5+1);
                sprintf(conf[6].filename,"%s.%s",basename,"count");
                // basename.radix
                conf[7].filename = malloc(base_length+5+1);
                sprintf(conf[7].filename,"%s.%s",basename,"radix");
                break;
        }

    } while(c != -1);
    // FILE *insert = fopen();

    // Setup rand(). getpid() guarantees seed refresh when test is ran
    // more than once per second (e.g. test.sh at small sizes).
    srand(time(NULL) * getpid());
    #ifndef TESTS
    #define TESTS 1
    #endif
    // run test
    for(int TEST_NO = 0; TEST_NO < TESTS; TEST_NO++)
    {
        test(test_size, logging, list_type, qi_threshold, mi_threshold, radix, qi_threshold, conf);
    }

}

// cut-off value for printing sorted arrays
#define MAX_OUTPUT_ARRAY 32
void test(long test_size, int logging, OrderingE list_type, int qi_threshold,
    int mi_threshold, int base, long max, struct sort conf[])
{
    // qi as max
    long *arr = generate_list(list_type, test_size, qi_threshold, logging);
    if(logging > 0)
    {
        print_array("Generated array:", arr, test_size, NO_SPECIAL);
    }
    int tabular_out = 1 ;//|| conf[0].filename && !logging;

    if(!tabular_out)
    {
        printf("\t%15s\t%7s\t%12s\t%12s\t%9s\n","","size","swaps","comparisons", "time");
    }
    // enable full logging
    // logging = DEBUG;

    clock_t start, stop;
    FILE *out = stdout;
    // run tests.
    if(conf[0].enabled)
    {
        if(conf[0].filename != NULL)
        {
            out = fopen(conf[0].filename, "a");
            if(out == NULL) {
                perror("Can't open file:");
                exit(EXIT_FAILURE);
            }
        }
        fprintf(out, "%-15s","insert sort:\t");
        // print_array("\nbefore", arr, test_size);
        start = clock();
        Result *insert = insert_sort(arr, test_size, logging);
        stop = clock();
        if(logging > 0 && test_size < MAX_OUTPUT_ARRAY)
        {
            print_array("\nsorted:", insert->array, test_size, NO_SPECIAL);
        }
        print_result(insert, test_size, stop-start, tabular_out, out);
        free(insert->array);
        if(out!=stdout) {
            fclose(out);
        }
    }

    if(conf[1].enabled)
    {
        if(conf[1].filename != NULL)
        {
            out = fopen(conf[1].filename, "a");
            if(out == NULL) {
                perror("Can't open file:");
                exit(EXIT_FAILURE);
            }
        }
        fprintf(out, "%-15s", "merge sort:\t");

        // print_array("\nbefore:", arr, test_size);
        start = clock();
        Result *merge = merge_sort(arr, test_size, logging);
        stop = clock();
        if(logging > 0 && test_size < MAX_OUTPUT_ARRAY)
        {
            print_array("\nsorted:", merge->array, test_size, NO_SPECIAL);
        }
        print_result(merge, test_size, stop-start, tabular_out, out);
        free(merge->array);
        if(out!=stdout) {
            fclose(out);
        }
    }

    if(conf[2].enabled)
    {
        if(conf[2].filename != NULL)
        {
            out = fopen(conf[2].filename, "a");
            if(out == NULL) {
                perror("Can't open file:");
                exit(EXIT_FAILURE);
            }
        }
        fprintf(out, "%-15s", "quick sort:\t");
        // print_array("\nbefore:", arr, test_size);
        start = clock();
        Result *quick = quick_sort(arr, test_size, logging);
        stop = clock();
        if(logging > 0 && test_size < MAX_OUTPUT_ARRAY)
        {
            print_array("\nsorted:", quick->array, test_size, NO_SPECIAL);
        }
        print_result(quick, test_size, stop-start, tabular_out, out);
        free(quick->array);
        if(out!=stdout) {
            fclose(out);
        }
    }

    if(conf[3].enabled)
    {
        if(conf[3].filename != NULL)
        {
            out = fopen(conf[3].filename, "a");
            if(out == NULL) {
                perror("Can't open file:");
                exit(EXIT_FAILURE);
            }
        }
        fprintf(out, "quickins (%d):\t", qi_threshold);
        // print_array("\nbefore", arr, test_size);
        start = clock();
        Result *quick_insert = quick_insert_sort(arr, test_size, logging, qi_threshold);
        stop = clock();
        if(logging > 0 && test_size < MAX_OUTPUT_ARRAY)
        {
            print_array("\nsorted:", quick_insert->array, test_size, NO_SPECIAL);
        }
        print_result(quick_insert, test_size, stop-start, tabular_out, out);
        free(quick_insert->array);
        if(out!=stdout) {
            fclose(out);
        }
    }

    if(conf[4].enabled)
    {
        if(conf[4].filename != NULL)
        {
            out = fopen(conf[4].filename, "a");
            if(out == NULL) {
                perror("Can't open file:");
                exit(EXIT_FAILURE);
            }
        }
        fprintf(out, "mergeins (%d):\t", mi_threshold);

        start = clock();
        Result *merge_insert = merge_insert_sort(arr, test_size, mi_threshold, logging);
        stop = clock();
        if(logging > 0 && test_size < MAX_OUTPUT_ARRAY)
        {
            print_array("\nsorted", merge_insert->array, test_size, NO_SPECIAL);
        }
        print_result(merge_insert, test_size, stop-start, tabular_out, out);
        free(merge_insert->array);
        if(out!=stdout) {
            fclose(out);
        }
    }

    // if(conf[5].enabled)
    // {
    //     if(conf[5].filename != NULL)
    //     {
    //         out = fopen(conf[5].filename, "a");
    //         if(out == NULL) {
    //             perror("Can't open file:");
    //             exit(EXIT_FAILURE);
    //         }
    //     }
    //     else
    //         { printf("%-15s","Yaroslavskiy:\t"); }
    //     start = clock();
    //     Result *yaro = yaro_quick_sort(arr, test_size, logging);
    //     stop = clock();
    //     if(logging > 0 && test_size < MAX_OUTPUT_ARRAY)
    //     {
    //         print_array("\nsorted", yaro->array, test_size, NO_SPECIAL);
    //     }
    //     print_result(yaro, test_size, stop-start, tabular_out, out);
    //     if(out!=stdout) {
    //         fclose(out);
    //     }
    // }

    if(conf[6].enabled)
    {
        if(conf[6].filename != NULL)
        {
            out = fopen(conf[6].filename, "a");
            if(out == NULL) {
                perror("Can't open file:");
                exit(EXIT_FAILURE);
            }
        }
        fprintf(out, "%-15s","counting:\t");
        start = clock();
        Result *counting = counting_sort(arr, test_size, logging);
        stop = clock();
        if(logging > 0 && test_size != MAX_OUTPUT_ARRAY)
        {
            print_array("\nsorted", counting->array, test_size, NO_SPECIAL);
        }
        print_result(counting, test_size, stop-start, tabular_out, out);
        free(counting->array);
        if(out!=stdout) {
            fclose(out);
        }
    }

    if(conf[7].enabled)
    {
        if(conf[7].filename != NULL)
        {
            out = fopen(conf[7].filename, "a");
            if(out == NULL) {
                perror("Can't open file:");
                exit(EXIT_FAILURE);
            }
        }
        fprintf(out, "radix (%5d):\t", base);
        // puts("");    
        start = clock();
        Result *radix = radix_sort(arr, test_size, base, logging);
        stop = clock();
        if(logging > 0 && test_size != MAX_OUTPUT_ARRAY)
        {
            print_array("\nsorted", radix->array, test_size, NO_SPECIAL);
        }
        print_result(radix, test_size, stop-start, tabular_out, out);
        free(radix->array);
        if(out!=stdout) {
            fclose(out);
        }
    }
    free(arr);
}

// Generators
long *generate_list_random(long size, long max)
{
    long *arr = malloc(sizeof(long) * size);
    for(long i = 0; i < size; i++)
    {
        if(rand()%512 > 255)
            arr[i] = rand()%(max+1);
        else
            arr[i] = rand()%(max+1);
    }
    return arr;
}

long *generate_list_ascending(long size)
{
    long *arr = malloc(sizeof(long) * size);
    for(long i = 0; i < size; i++)
    {
        arr[i] = i;
    }
    return arr;
}

long *generate_list_descending(long size)
{
    long *arr = malloc(sizeof(long) * size);
    for(long i = 0; i < size; i++)
    {
        arr[i] = size-i;
    }
    return arr;
}

long *generate_list(OrderingE list_type, long test_size, long max, int logging) {
    long *arr = NULL;
    switch (list_type)
    {
        case Ascending:
            arr = generate_list_ascending(test_size);
            if(logging > 1)
            {
                printf("Generating scending list of size %ld.\n", test_size);
            }
            break;
        case Descending:
            arr = generate_list_descending(test_size);
            if(logging > 1)
            {
                printf("Generating descending list of size %ld.\n", test_size);
            }
            break;
        case Random:
            arr = generate_list_random(test_size, max);
            if(logging > 1)
            {
                printf("Generating random list of size %ld.\n", test_size);
            }
            break;
    }
    return arr;
}
