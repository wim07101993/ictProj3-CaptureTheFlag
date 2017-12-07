<div class="modalAddQuestion"
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
<div class="modal modalAddQuestion" style="position:fixed; top:30vh; z-index: 100 !important;">
    <div class="modal-content">
        <form class="addquestionform" method="post" action="/vragen/add">
        
        </form>
    </div>
    <div class="modal-footer">
        <a id="closeAdd" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Annuleer</a>
        <a id="saveAdd" class="modal-action modal-close waves-effect waves-white green btn-flat" style="color:white;">Opslaan</a>
    </div>
</div>

<script>
function addAddListener(){
    $(".addquestion").click(function(){
        $(".modalAddQuestion").fadeIn(1000);
        var generateView="";
        generateView+=csrf;
            generateView+="<h4>Vraag</h4>";       
            generateView+="<input id='question' name='question' value='' />";
        $(".addquestionform").html(generateView)
    });
    $("#closeAdd").click(function(){
           $(".modalAddQuestion").fadeOut(1000);
    })
    $("#saveAdd").click(function(){
        if(document.getElementById("question").value != ''){
            $(".addquestionform").submit();
        } else {
            document.getElementById("question").className = "Invalid";
        }
    })
}
</script>