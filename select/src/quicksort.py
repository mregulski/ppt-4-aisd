#!/usr/bin/python3
from median import select, generate_random
import random

logging = 0
cmps = 0
def comparison():
    global cmps
    cmps += 1
    return True


def partition(array, start, end, mode='r', depth=0):
    global logging
    pivot = -404
    if mode == 'r':
        pivot = array[random.randrange(start, end+1)]
    elif mode == 'f':
        pivot = array[start]
    elif mode == 'm':
        pivot = select(array, (end-start)//2)
    elif mode == 'l':
        pivot = array[end]
    if logging >= 5:
        print("{indent}partitioning array[{start}:{end}] around \x1b[1;33m{pivot}\x1b[0m"
              .format(indent=" "*depth,
                      start=start,
                      end=end+1,
                      pivot=pivot))
        print("{}before: {}".format(" "*depth, array[start:end+1]))
        print("{}pivot: {}".format(" "*depth, pivot))

    i = start-1
    j = end+1
    while(comparison() and i < j):
        j -= 1
        while(comparison() and array[j] > pivot):
            j -= 1
        i += 1
        while(comparison() and array[i] < pivot):
            i += 1
        if(comparison() and i < j):
            array[i], array[j] = array[j], array[i]
    if logging >= 5:
        print("{}after (j={}):".format(" "*depth, j), end='')
        print_array_marked(array[start:end+1], [j-start])
    return j


def quicksort(array, start, end, mode='r', depth=0):
    if start < end:
        split = partition(array, start, end, mode, depth+1)
        quicksort(array, start, split)
        quicksort(array, split+1, end)
    return


if __name__ == '__main__':
    import time
    max_size = 5000
    test_count = 30
    r = open('./qs.r.out', 'a')
    f = open('./qs.f.out', 'a')
    m = open('./qs.m.out', 'a')
    l = open('./qs.l.out', 'a')
    for i in range(test_count):
        print("\x1b[1;36mTest #{}\x1b[0m".format(i+1))
        for size in range(100, max_size+1, 100):
            array = generate_random(size)
            print("\r\x1b[2Ksize: {}...".format(size), end=' ')
            cmps = 0
            t0 = time.perf_counter()
            quicksort(array, 0, len(array)-1, 'r')
            t1 = time.perf_counter()
            r.write('{size:10d}\t{cmps:10d}\t{time:14f}\n'.format(size=size, cmps=cmps, time=t1-t0))
            # print('random:', cmps, sep='\t')
            cmps = 0
            t0 = time.perf_counter()
            quicksort(array, 0, len(array)-1, 'f')
            t1 = time.perf_counter()
            f.write('{size:10d}\t{cmps:10d}\t{time:10f}\n'.format(size=size, cmps=cmps, time=t1-t0))
            # print('first:', cmps, sep='\t')
            cmps = 0
            t0 = time.perf_counter()
            quicksort(array, 0, len(array)-1, 'm')
            t1 = time.perf_counter()
            m.write('{size:10d}\t{cmps:10d}\t{time:10f}\n'.format(size=size, cmps=cmps, time=t1-t0))
            # print('middle:', cmps, sep='\t')
            cmps = 0
            t0 = time.perf_counter()
            quicksort(array, 0, len(array)-1, 'l')
            t1 = time.perf_counter()
            l.write('{size:10d}\t{cmps:10d}\t{time:10f}\n'.format(size=size, cmps=cmps, time=t1-t0))
            # print('last:', cmps, sep='\t')
        print("done.")
    r.close()
    f.close()
    m.close()
    l.close()
