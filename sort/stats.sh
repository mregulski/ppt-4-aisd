#!/bin/zsh
# statistics generator for sorting algorithms
if [ $# -ne 5 ]; then
    echo "usage: ./stats.sh <test_amount> <max_size> <prefix>"
    exit 1
fi
test_amount=$1
max_size=$2
prefix=$3
max_radix=$4
max_breakpoint=$5

for i in {1..$test_amount}; do
    size=$max_size
    breakpoint=$max_breakpoint
    radix=$max_radix
    output=$prefix
    while (($size > 0)); do
        echo -n "\r$(tput el)$(tput bold)size:$(tput sgr0) $size"
        # bin/sort -s $size -t random -o $output.$size.rand
        # echo "\nsDone basic tests @ size=$size."
        # while (($breakpoint > 1)); do
        #     bin/sort -s $size -t random -o $output.rand -fDE -i $breakpoint -m $breakpoint
        #     ((breakpoint-=1))
        # done
        # echo "\nDonse breakpoint tests @ size=$size."
        # while (($radix > 1)); do
            bin/sort -s $size -fH -i 100000 -r 1 >> $output.$size.$radix.rand
            # let radix=$radix-32
        # done
        # radix=20480
        # while (($radix > 512)); do
        #     bin/sort -s $size -fH -r $radix >> $output.$size.$radix.rand
        #     let radix=$radix-1024
        # done


        # echo "\nDone radix tests @ size=$size."
        let size=$size-500
        echo -n ""
    done
    echo "\r$(tput el)$(tput setaf 7)[$(date +"%H:%M:%S")]$(tput sgr0) Test $(tput bold)#$i$(tput sgr0) done."
done
echo "\r$(tput el)All tests done. Maximum size: $2."
