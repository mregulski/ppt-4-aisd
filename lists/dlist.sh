#!/bin/zsh
TOT1=0;
TOT2=0;
TOT3=0;
TOT0=0;
echo -n "0/$1"
for j in {1..$1}; do
    if (($j % 10 == 0)); then
        echo -n "\r$j / $1";
    else
        echo -n ".";
    fi
    AVG3=0;
    AVG2=0;
    AVG1=0;
    AVG0=0;
    RES=$(for i in {1..100}; do bin/dlist 1000 $2; done);
    N3=$(echo $RES | grep 'OVER 3'| wc -l);
    N2=$(echo $RES | grep 'OVER 2'| wc -l);
    N1=$(echo $RES | grep 'OVER 1'| wc -l);
    N0=$(echo $RES | grep 'OVER +'| wc -l);
    TOT3=$((($TOT3 + $N3)));
    TOT2=$((($TOT2 + $N2)));
    TOT1=$((($TOT1 + $N1)));
    TOT0=$((($TOT0 + $N0)));
    #AVG3=$(echo "scale=3;($N3-$AVG3)/$j" | bc);
    #AVG2=$(echo "scale=3;($N2-$AVG2)/$j" | bc);
    #AVG1=$(echo "scale=3;($N1-$AVG1)/$j" | bc);
    #AVG0=$(echo "scale=3;($N0-$AVG0)/$j" | bc);
done;
echo
printf "%-27s $TOT1\n" "TOT1: delta > 0.1:"
printf "%-27s $TOT2\n" "TOT2: 0.1 > delta > 0.01:";
printf "%-27s $TOT3\n" "TOT3: 0.01 > delta > 0.001:";
printf "%-27s $TOT0\n" "TOT+: 0.001 > delta:";
TOTAL=$(echo "$TOT1 + $TOT2 + $TOT3 + $TOT0" | bc);
CASES=$(($1 * 1000));
PERCENT=$(($TOTAL.0/$CASES * 100));
printf "first > last in $TOTAL  out of $CASES cases (%.2f%%)" $PERCENT;
echo

#printf "%-27s $AVG1\n" "AVG1: delta > 0.1:"
#printf "%-27s $AVG2\n" "AVG2: 0.1 > delta > 0.01:";
#printf "%-27s $AVG3\n" "AVG3: 0.01 > delta > 0.001:";
#printf "%-27s $AVG0\n" "AVG+: 0.001 > delta:";
#echo "sum: " $(echo "$AVG1 + $AVG2 + $AVG3 + $AVG0" | bc);
