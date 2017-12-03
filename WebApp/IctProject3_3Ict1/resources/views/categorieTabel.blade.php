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
                        <th colspan="2" style="height: 75px;">
                            <input id="search" placeholder=" Search..." class="grey"  id="search" ></input>
                        </th>
                    </thead>
                    <tbody class="grey lighten-4" id="table">

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
            var filteredCategorieën= categorieën;
            var csrf='<?php echo csrf_field()?>';
        </script>
        <?php
            include (__DIR__."/../../../resources/views/categorieënmodal/edit.blade.php");
            include (__DIR__."/../../../resources/views/categorieënmodal/delete.blade.php");
            include (__DIR__."/../../../resources/views/categorieënmodal/add.blade.php");
            include (__DIR__."/../../../resources/views/library/generateCategorieënTabel.php");   
        ?>
    </body>
@endsection