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
                        <th>Vraag</th> 
                        <th>Antwoorden</th>
                        <th> </th>
                        <th> </th>
                    </thead>
                    <tbody class="grey lighten-4">
                        <?php foreach ($vragen as $vraag) { ?>
                        <tr>
                        <td>
                            <?php echo($vraag["Question_ID"]); ?>
                        </td>
                        <td>
                            <?php echo($vraag["Question"]); ?>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn amber accent-3 editanwsers" href="antwoorden/<?= $vraag["Question_ID"]?>">Antwoorden</a>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn green accent-3 editquestion" id="<?=($vraag["Question_ID"]); ?>">Bewerk</a>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn red" href="vragen/delete/<?= $vraag["Question_ID"]?>">Verwijder</a>
                        </td>
                        </tr>
                        <?php } ?>
                        <tr class="grey lighten-3" style="border:none;">
                        <td>                 
                            <a class="btn-floating btn-large waves-effect waves-light green accent-3 addquestion"><i class="material-icons">add</i></a>
                        </td>
                        </tr>
                    </tbody>
                    </table>
                </td>
                </tr>
            </tbody>
        </table>
        <script>
         var vragen= <?php echo json_encode( $vragen) ?> ;
         var csrf='<?php echo csrf_field()?>';
         console.log(vragen);
         </script>
        <?php
            include (__DIR__."/../../../resources/views/vragenmodal/edit.blade.php");
            include (__DIR__."/../../../resources/views/vragenmodal/add.blade.php");
        ?>
    </body>
@endsection

