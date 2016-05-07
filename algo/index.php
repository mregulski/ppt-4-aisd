<?php
require_once('Page.php');
$content = file_get_contents('content/index.html');
$page = new AlgorithmPage(0, "Hello!", $content);
?>
<?=$page->Render();?>
