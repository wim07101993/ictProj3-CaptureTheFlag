<div class="modalAddAnswer"
    style="background: black;
           background-color: #00000070;
           position: fixed;
           top: 0;
           bottom: 0;
           left: 0;
           right: 0;
           z-index: 1;
           display:none;" >
</div>
<div class="modal modalAddAnswer" style="position:fixed; top:30vh;  z-index: 100 !important;">
    <div class="modal-content">
        <form class="addanswerform" method="post" action="/antwoorden/add">
        
        </form>
    </div>
    <div class="modal-footer">
        <a id="closeAdd" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Annuleer</a>
        <a id="saveAdd" class="modal-action modal-close waves-effect waves-white green accent-3 btn-flat" style="color:white;">Opslaan</a>
    </div>
</div>

<script>
 $(document).ready(function(){
    $(".addanswer").click(function(){
        $(".modalAddAnswer").fadeIn(1000);
        var generateView="";
        generateView+=csrf;
            generateView+="<h4>Antwoord</h4>"
            generateView+="<input id='answer' name='answer' value='' required />";
            generateView+="<input type='checkbox' class='filled-in' id='correct' name='correct'/>"
            generateView+="<label for='correct'>Correct</label>"
        generateView+="<input name='question_id' value='"+question_id+"' style='display:none;'/>";
        $(".addanswerform").html(generateView)
    });
    $("#closeAdd").click(function(){
           $(".modalAddAnswer").fadeOut(1000);
    })
    $("#saveAdd").click(function(){
        if(document.getElementById("answer").value != ''){
            $(".editanswerform").submit();
        } else {
            document.getElementById("answer").className = "Invalid";
        }
    })
});
</script>