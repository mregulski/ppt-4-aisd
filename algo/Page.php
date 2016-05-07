<?php
$Template = file_get_contents('template/template.html');

$Pages = [
    ['id' => 0,  'href' => 'index.php',       'name' => 'Index'],
    ['id' => 1,  'href' => 'euclid.php',      'name' => 'Algorytm Euklidesa'],
    ['id' => 2,  'href' => 'ext_euclid.php',  'name' => 'Rozszerzony Algorytm Euklidesa'],
    ['id' => 3,  'href' => 'prime.php',       'name' => 'Test pierwszości'],
    ['id' => 4,  'href' => 'sieve.php',       'name' => 'Sito Erastotenesa'],
    ['id' => 5,  'href' => 'insert_sort.php', 'name' => 'Sortowanie przez wstawianie'],
    ['id' => 6,  'href' => 'merge_sort.php',  'name' => 'Sortowanie przez scalanie'],
    ['id' => 7,  'href' => 'bin_search.php',  'name' => 'Wyszukiwanie binarne'],
    ['id' => 8,  'href' => 'hanoi.php',       'name' => 'Wieże Hanoi'],
    ['id' => 9,  'href' => 'queens.php',      'name' => 'Problem hetmanów'],
    ['id' => 10, 'href' => 'knight.php',      'name' => 'Problem skoczka szachowego']
];
$Config = [
    "ScriptPath" => 'scripts',
    "StylePath" => 'styles'
];

class AlgorithmPage {
    private $id = -1;
    private $title = "";
    private $scripts = [];
    private $styles = [];
    private $content = "";

    function __construct($id, $title, $content) {
        $this->id = $id;
        $this->title = $title;
        $this->content = $content;
    }

    function RegisterScript($script) {
        $this->scripts[] = $script;
    }

    function RegisterStyle($style) {
        $this->styles[] = $style;
    }

    function Render() {
        $scripts = $this->ScriptBlock();
        $styles = $this->StyleBlock();
        $menuitems = $this->GenerateMenu();
        return $this->GetHTML($scripts, $styles, $menuitems);
    }

    private function GetHTML($scripts, $styles, $menuitems) {
        $template = file_get_contents('template/template.html');
        $html = str_replace(["{{Title}}", "{{Scripts}}", "{{Styles}}", "{{MenuItems}}", "{{PageContent}}"],
                    [$this->title, $scripts, $styles, $menuitems,$this->content],
                    $template);
        return $html;
    }

    private function ScriptBlock() {
        $scriptTags = "";
        foreach($this->scripts as $script) {
            $scriptTags .= "<script src='{$script}'></script>\n";
        }
        unset($script);
        return $scriptTags;
    }

    private function StyleBlock() {
        $styleTags = "";
        foreach($this->styles as $style) {
            $styleTags .= "<link rel='stylesheet' href='$style'/>\n";
        }
        unset($style);
        return $styleTags;
    }

    public function Footer() {
        global $Footer;
        return $Footer;
    }
    private function GenerateMenu() {
        global $Pages;
        $menu_class = 'algorytm';
        $menu_entry = "<li class='$menu_class'><a href='{{Href}}' class='{{Class}}'>{{Name}}</a></li>";
        $menu = "<ul>\n";
        for($i = 0; $i < count($Pages); $i++) {
            $classes="";
            if((int) $Pages[$i]["id"] === $this->id) {
                $classes.="active";
            };
            $element = (string) str_replace("{{Href}}", (string) $Pages[$i]["href"], $menu_entry);
            $element = (string) str_replace("{{Name}}", (string) $Pages[$i]["name"], $element);
            $element = (string) str_replace("{{Class}}", (string) $classes, $element);
            $menu .= $element."\n";

        };
        $menu .= "</ul>\n";
        return $menu;
    }

}
?>
