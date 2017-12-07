@extends('layouts.app')

@section('content')
    <body class="grey lighten-3">
        <table style="width:100%">
            <tbody>
                <tr>
                <td>
                    <table class="centered">
                    <tbody>
                    <tr>
                    <td style="left:2vw; position:absolute">
                        <a class="btn-large waves-effect waves-light white" style="color:black;" href="/vragen"><i class="material-icons left">arrow_back</i>Back</a>
                        
                    </td>

                    <td>
                        <h4 class="center"><?php echo($vraag["Question"]); ?></h4>
                    </td>
                    </tr>
                </tbody>
                    </table>
                </td>
                </tr>
                <tr>
                <td>
                    <table class="centered bordered">
                    <thead>
                        <th>Antwoord</th>
                        <th>Correct</th>
                        <th> </th>
                        <th> </th>
                    </thead>
                    <tbody class="grey lighten-4">
                        <?php foreach ($antwoorden as $antwoord) { ?>
                        <tr>
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
                            <a class="waves-effect waves-light btn green editanswer" id="<?=($antwoord["Answer_ID"]); ?>">Bewerk</a>
                        </td>
                        <td>
                            <a class="waves-effect waves-light btn red deleteanswer" id="<?=($antwoord["Answer_ID"]); ?>">Verwijder</a>
                        </td>
                        </tr>
                        <?php } ?>
                        <tr class="grey lighten-3" style="border:none;">
                        <td>                 
                            <a class="btn-floating btn-large waves-effect waves-light green addanswer"><i class="material-icons">add</i></a>
                        </td>
                        </tr>
                    </tbody>
                    </table>
                </td>
                </tr>
            </tbody>
        </table>
        <script>

        document.getElementById("categorieënTab").className = " ";
        document.getElementById("vragenTab").className = "active";
        
         var antwoorden= <?php echo json_encode( $antwoorden) ?> ;
         var question_id= <?php echo json_encode( $question_id) ?>;
         var csrf='<?php echo csrf_field()?>';
         console.log(antwoorden);
         console.log(question_id);
         </script>
        <?php
            include (__DIR__."/../../../resources/views/antwoordenmodal/delete.blade.php");
            include (__DIR__."/../../../resources/views/antwoordenmodal/edit.blade.php");
            include (__DIR__."/../../../resources/views/antwoordenmodal/add.blade.php");
        ?>
    </body>
@endsection
