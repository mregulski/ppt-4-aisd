<?php
require_once('Page.php');
$content = file_get_contents('content/merge_sort.html');
$page = new AlgorithmPage(6, "Algorytmy", $content);
$page->RegisterScript('scripts/mergeSort.js');
$page->RegisterStyle('styles/awwwsom.css');
?>
<?=$page->Render();?>
