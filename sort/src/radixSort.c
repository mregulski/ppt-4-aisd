#include "util.h"
#include "sort.h"
#include "math.h"

Result *radix_sort(long *arr, long len, int base, int logging)
{
    long *array = malloc(len * sizeof(long));
    memcpy(array, arr, len*sizeof(long));
    long max = array[0];
    Result *r = result();
    r->array = array;
    for(int i = 0; i < len; i++)
    {
        if(array[i] > max)
            max = array[i];
    }

    if(base == 1) {    //select optimal
        // printf("max: %d\n", max);
        // printf("0s: %d\n", __builtin_clrsbl(max));
        int bitlen = sizeof(long)*8 - __builtin_clrsbl(len); // long - leading 0s
        printf("bitlen: %d\n", bitlen);
        if(bitlen < log2(len)) {
            base = bitlen;
        }
        else {
            base = log2(len);
        }
    }
    // printf("base: %d\n", base);
    for(int exp = 1; max/exp > 0; exp *= base)
    {
        if(logging > 1) {
            printf("digit: %is, array maximum: %ld, base: %d\n", exp, max, base);
        }
        r = counting_sort_radix(r->array, len, base, exp, r, logging);
        if(logging > 1) {
            print_array("result so far:", r->array, len, NO_SPECIAL);
            puts("");
        }
    }
    return r;
}
