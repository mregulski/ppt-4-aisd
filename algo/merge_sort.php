<?php
require_once('Page.php');
$Content =<<<EOT
<article>
    <h2>Sortowanie przez scalanie</h2>
    <p>Rekurencyjny algorytm sortowania wykorzystujący metodę dziel i zwyciężaj.</p>
    <h3>Schemat działania</h3>
    <ol>
        <li>Podziel zestaw danych na dwie równe części.</li>
        <li>Posortuj każdą część przez scalanie, chyba że został tylko jeden element.</li>
        <li>Połącz posortowane podciągi w jeden.</li>
    </ol>
    <section>
        <h3>Pseudo-kod funkcji:</h3>
        <pre>
// posortuj Array[start...end]
mergeSort(Array, start, end):
if start != end:
center &lt;- floor((start+end)/2)
mergeSort(Array, start, center)
mergeSort(Array, center+1, end)
merge(Array, start, center, finish)

// scal A[start...center] z A[center+1 ... end]
merge(A, start, center, end):
i &lt;- start, j &lt;- center+1
A2 &lt;- [] // tablica pomocnicza
while i &lt;= center and j &lt;= end
    if A[j] &lt; A[i]
        append A[j] to A2
        j++
    else
        append A[i] to A2
        i++
if one of the halves is empty:
    append the rest of the other to the end of A2
    copy A2 to A
        </pre>
    </section>
</article>
<div id="simulator">
    <h3>Przykład działania</h3>
    <p>Wprowadź dane do posortowania oddzielone przecinkami</p>
    <input type="text" placeholder="dane do sortowania" id="array"/>
    <button id="runButton" name="run" disabled>Sortuj</button>
    <div id="output">
    </div>
</div>
EOT;
$page = new AlgorithmPage(6, "Algorytmy");
$page->RenderPage($Content);


 ?>
