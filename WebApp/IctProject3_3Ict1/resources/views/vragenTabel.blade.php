@extends('layouts.app')

@section('content')
    <body class="grey lighten-3">
        <table style="width:100%">
            <tbody>
                <tr>
                <td>
                    <table class="centered bordered">
                    <thead  >
                        <th>ID</th>
                        <th>Vraag</th> 
                        <th>Antwoorden</th>
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
            document.getElementById("categorieënTab").className = " ";
            document.getElementById("vragenTab").className = "active";

            var vragen= <?php echo json_encode( $vragen) ?> ;
            var filteredVragen=vragen; 
            var csrf='<?php echo csrf_field()?>';
        </script>
        <?php     
            include (__DIR__."/../../../resources/views/vragenmodal/delete.blade.php");
            include (__DIR__."/../../../resources/views/vragenmodal/edit.blade.php");
            include (__DIR__."/../../../resources/views/vragenmodal/add.blade.php");
            include (__DIR__."/../../../resources/views/library/generateVragenTabel.php");                            
        ?>
    </body>
@endsection

