@extends('layouts.app')

@section('content')
    <body class="grey lighten-3">
        <table style="width:100%">
            <tbody>
                <tr>
                <td>
                    <table class="centered">
                    <tbody>
                        <td>
                        <h4>
                            <a class="btn-floating btn-large waves-effect waves-light grey lighten-5" href="/vragen"><i class="material-icons left" style="color:black;">arrow_back</i></a>
                            <?php echo($vraag["Question"]); ?>
                        </h4>
                        </td>
                    </tbody>
                    </table>
                </td>
                </tr>
                <tr>
                <td>
                    <table class="centered bordered">
                    <thead>
                        <th>ID</th> 
                        <th>Antwoord</th>
                        <th>Correct</th>
                        <th> </th>
                        <th> </th>
                    </thead>
                    <tbody class="grey lighten-4">
                        <?php foreach ($antwoorden as $antwoord) { ?>
                        <tr>
                        <td>
                            <?php echo($antwoord["Answer_ID"]); ?>
                        </td>
                        <td>
                            <?php echo($antwoord["Answer"]); ?>
                        </td>
                        <td>
                            <?php $checked="";
                            if($antwoord["Correct"]==1){
                                $checked="checked='checked'";
                            } ?>
                            <input type="checkbox" class="filled-in" id="filled-in-box" disabled="disabled" <?= $checked ?>/>
                            <label for=""></label>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn green accent-3 editanswer" id="<?=($antwoord["Answer_ID"]); ?>">Bewerk</a>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn red accent-3" href="/antwoorden/delete/<?= $antwoord["Answer_ID"]?>">Verwijder</a>
                        </td>
                        </tr>
                        <?php } ?>
                        <tr class="grey lighten-3" style="border:none;">
                        <td>                 
                            <a class="btn-floating btn-large waves-effect waves-light green accent-3 addanswer"><i class="material-icons">add</i></a>
                        </td>
                        </tr>
                    </tbody>
                    </table>
                </td>
                </tr>
            </tbody>
        </table>
        <script>
         var antwoorden= <?php echo json_encode( $antwoorden) ?> ;
         var question_id= <?php echo json_encode( $question_id) ?>;
         var csrf='<?php echo csrf_field()?>';
         console.log(antwoorden);
         console.log(question_id);
         </script>
        <?php
            include (__DIR__."/../../../resources/views/antwoordenmodal/edit.blade.php");
            include (__DIR__."/../../../resources/views/antwoordenmodal/add.blade.php");
        ?>
    </body>
@endsection
