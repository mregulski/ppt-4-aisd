#!/usr/bin/python3
import random
import argparse
import sys
import math
logging = 0
cmps = 0


def comparison():
    global cmps
    cmps += 1
    return True


def partition(array, start=0, end=-1, depth=0):
    if(end == -1):
        end = len(array)-1

    pivot = array[random.randrange(start, end+1)]
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
        j-=1
        while(comparison() and array[j] > pivot):
            j-=1
        i+=1
        while(comparison() and array[i] < pivot):
            i+=1
        if(comparison() and i < j):
            array[i], array[j] = array[j], array[i]
    if logging >= 5:
        print("{}after (j={}):".format(" "*depth, j), end='')
        print_array_marked(array[start:end+1], [j-start])
    return j



def random_select(array, k, start=0, end=-1, depth=0):
    if(end == -1):
        end=len(array)-1

    if(start == end):
        return array[start]

    split = partition(array, start, end, depth+1)
    n = split - start + 1
    if (comparison() and k <= n):
        if logging >=5:
            print("{indent}k={k} <= n={n}, searching element #{k} in array[{start}:{end}]"
                  .format(k=k, n=n, start=start, end=split+1, indent=" "*depth))
        return random_select(array, k, start, split, depth+1)
    else:
        if logging >=5:
            print("{indent}k={k} > n={n}, searching element #{i} in array[{start}:{end}]"
                  .format(k=k, n=n, i=k-n, start=split+1, end=end+1, indent=" "*depth))
        return random_select(array, k-n, split+1, end, depth+1)


def select(array, k, depth=0):
    global logging
    if(len(array) <= 5):
        array.sort()
        # print('array:', array)
        # print("k:", k)
        return(array[k-1])

    fives = [array[i:i+5] for i in range(0, len(array), 5)]

    if(logging >= 5):
        i = 0
        for five in fives:
            print("{}group[{}]:".format(" "*depth, i), five)
            i += 1

    x = []
    ceil = math.ceil(len(array)/5)
    # print("ceil: ", ceil)
    # print("lenfives:" ,len(fives))
    for i in range(ceil):
        m_idx = math.ceil(len(fives[i])/2);
        # print("m_idx:", m_idx)
        x.append(select(fives[i], m_idx))

    if(logging >= 1):
        print("{}medians:".format(" "*depth), x)

    M = select(x, math.ceil(len(x)/2), depth+1)

    if(logging >= 1):
        print("{}Median-of-medians:".format(" "*depth), M)
    P1, P2, P3 = [], [], []
    for val in array:
        if (comparison() and val < M):
            P1.append(val)
        elif (comparison() and val == M):
            P2.append(val)
        else:
            P3.append(val)

    if logging >= 1:
        print("{}P1 < Median ({} elem.):".format(" "*depth, len(P1)), P1)
        print("{}P2 = Median ({} elem.):".format(" "*depth, len(P2)), P2)
        print("{}P3 > Median ({} elem.):".format(" "*depth, len(P3)), P3)
    # print("{}k:".format(" "*depth), k)
    if (comparison() and k <= len(P1)):
        if logging >= 1:
            print("{0}k={1} <= len(P1), searching for element #{1}. in P1".format(" "*depth, k))
        return select(P1, k, depth+1)
    elif (comparison() and k > len(P1)+len(P2)):
        if logging >= 1:
            print("{}k={} > len(P1)+len(P2), searching for element #{} in P3"
                  .format(" "*depth, k, k-len(P1)-len(P2)))
        return select(P3, k-len(P1)-len(P2), depth+1)
    else:
        if logging >= 1:
            print("{}len(P1) < k={} < len(P1)+len(P2), returning median ({})"
                  .format(" "*depth, k, M))
        return M


def generate_random(size):
    x = []
    for i in range(size):
        x.append(random.randrange(size*2))
    return x


def generate_perm(size):
    x = [0]*size
    used = [0]*(size)
    for i in range(size):
        while(True):
            val = random.randint(1, size)
            x[i] = val
            if used[val-1] != 1:
                break
        used[val-1] = 1
    return x


def print_array_marked(array, mark_indices):
    print('[', end='')
    for i in range(len(array)):
        print("{col_start}{var}{col_end}{comma}".format(var=array[i],
            col_start="\x1b[1;33m" if i in mark_indices else "",
            col_end="\x1b[0m",
            comma="," if i < len(array) - 1 else ''), end='')
    print(']')


def main(args):
    global cmps
    array = args.generator(args.size)
    if(args.interactive):
        print("data:", array)
        input("press ENTER to find element #{} deterministically.".format(args.position))
        print("\x1b[1melement #{}\x1b[0m: \x1b[1;33m{}\x1b[0m".format(args.position, select(array[:], args.position)))
        input("press ENTER to find element #{} randomly.".format(args.position))
        print("\x1b[1melement #{}\x1b[0m: \x1b[1;33m{}\x1b[0m".format(args.position, random_select(array[:], args.position)))
        input("press ENTER to check with sorted data")
        print_array_marked(sorted(array), [args.position-1])
    else:
        test_count = 10
        max_size = 10000
        for i in range(test_count):
            print("\x1b[1;36mTest #{}\x1b[0m".format(i+1))
            rs_out = open('./random.out', 'a+')
            s_out = open('./select.out', 'a+')
            for size in range(100, max_size+1, 100):
                print("\r\x1b[2Ksize: {}...".format(size),end=' ')
                array = generate_random(size)
                k = random.randrange(size)
                cmps = 0
                x = random_select(array[:], k)
                # print("cmps:",cmps,end=' ')
                rs_out.write("{:8d}\t{:8d}\t{:8d}\n".format(size, cmps, k))
                cmps = 0
                x = select(array[:], k)
                s_out.write("{:8d}\t{:8d}\t{:8d}\n".format(size, cmps, k))
                # print("cmps:",cmps,end=' ')
            print("done.".format(size=size))

            rs_out.close()
            s_out.close()



if __name__ == '__main__':
    def generator(string):
        if string in ['perm', 'permutation']:
            value = generate_perm
        else:
            value = generate_random
        return value

    parser = argparse.ArgumentParser()
    parser.add_argument('--size', '-s', type=int, default=20)
    parser.add_argument('--position', '-i', type=int, default=10)
    parser.add_argument('--generator', '-t', type=generator, default="rand")
    parser.add_argument('--logging', '-v', type=int, default=0)
    parser.add_argument('--interactive', action='store_const', const=True, default=False)
    args = parser.parse_args()
    logging = 5 if args.interactive else args.logging
    main(args)
