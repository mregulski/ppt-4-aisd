<?php
require_once('Page.php');
$content = file_get_contents('content/sieve.html');
$page = new AlgorithmPage(4, "Hello!", $content);
$page->RegisterScript('scripts/sieve.js');
$page->RegisterStyle('styles/sieve.css');
?>
<?=$page->Render();?>
