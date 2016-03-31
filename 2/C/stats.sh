#!/bin/zsh
# statistics generator for sorting algorithms
if [ $# -ne 4 ]; then
    echo "usage: ./stats.sh <test_amount> <max_size> <prefix> <test_name>"
    echo "output files: prefix.size.{1..test_amount}.timestamp"
    exit 1
fi
test_amount=$1
max_size=$2
prefix=$3
name=$4

for i in {1..$test_amount}; do
    size=$max_size
    output=$prefix.$name.$size.$i.$(date +%m%d%H%M)
    printf "%13s\t%7s\t%12s\t%12s\n" "" "size" "swaps" "comparisons" >> $output
    while (($size > 0)); do
        echo -n "\r$(tput el)size: $size"
        ./bin/$name -s $size -t random >> $output
        #echo "" >> $output
        ((size-=100))
    done
done
echo "\r$(tput el)Done. Maximum size: $2."
