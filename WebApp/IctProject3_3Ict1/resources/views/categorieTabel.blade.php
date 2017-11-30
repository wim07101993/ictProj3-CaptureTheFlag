@extends('layouts.app')

@section('content')
    <body class="grey lighten-3">
        <table style="width:100%">
            <tbody>
                <tr>
                <td>
                    <table class="centered bordered">
                    <thead>
                        <th>ID</th>
                        <th>Categorie</th>
                        <th> </th>
                        <th> </th>
                    </thead>
                    <tbody class="grey lighten-4">
                        <?php foreach ($categorieën as $categorie) { ?>
                        <tr>
                        <td>
                            <?php echo($categorie["Category_ID"]); ?>
                        </td>
                        <td>
                            <?php echo($categorie["Name"]); ?>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn green accent-3 editcategory <?php if ($categorie["Category_ID"] == 1){ echo("disabled");} ?>" id="<?=($categorie["Category_ID"]); ?>" >Bewerk</a>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn red <?php if ($categorie["Category_ID"] == 1){ echo("disabled");} ?>" href="categorieën/delete/<?= $categorie["Category_ID"]?>">Verwijder</a>
                        </td>
                        </tr>
                        <?php } ?>
                        <tr class="grey lighten-3" style="border:none;">
                        <td>                 
                            <a class="btn-floating btn-large waves-effect waves-light green accent-3 addcategory"><i class="material-icons">add</i></a>
                        </td>
                        </tr>
                    </tbody>
                    </table>
                </td>
                </tr>
            </tbody>
        </table>
        <script>
        document.getElementById("vragenTab").className = " ";
        document.getElementById("categorieënTab").className = "active";

         var categorieën = <?php echo json_encode( $categorieën) ?> ;
         var csrf='<?php echo csrf_field()?>';
         </script>
        <?php
            include (__DIR__."/../../../resources/views/categorieënmodal/edit.blade.php");
            include (__DIR__."/../../../resources/views/categorieënmodal/add.blade.php");
        ?>
    </body>
@endsection