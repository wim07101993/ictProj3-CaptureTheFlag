<div class="modalEditAnswer"
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
<div class="modal modalEditAnswer" style="position:fixed; top:30vh; z-index: 100 !important;">
    <div class="modal-content">
        <form class="editanswerform" method="post" action="/antwoorden/edit">  
            
        </form>
    </div>
    <div class="modal-footer">
        <a id="close" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Annuleer</a>
        <a id="save" class="modal-action modal-close waves-effect waves-white green btn-flat" style="color:white;">Opslaan</a>
    </div>
</div>

<script>
 $(document).ready(function(){
    $(".editanswer").click(function(){
        var id = this.id;
        var antwoord =antwoorden.filter(function(antwoord){
            return antwoord.Answer_ID == id;
        });
        antwoord=antwoord[0]
        $(".modalEditAnswer").fadeIn(1000);
        var generateView="";
        generateView+=csrf;
            generateView+="<input name='id' value='"+antwoord.Answer_ID+"' style='display:none;'/>";
            generateView+="<h4>Antwoord</h4>"; 
            generateView+="<input id='editAnswer' name='answer' value='"+antwoord.Answer+"' />";
                checked="";
                if(antwoord["Correct"]==1){
                    checked="checked='checked'";
                }
            generateView+="<input type='checkbox' class='filled-in' id='editCorrect' name='correct' "+checked+" />"
            generateView+="<label for='editCorrect'>Correct</label>"
        $(".editanswerform").html(generateView)
    });
    $("#close").click(function(){
           $(".modalEditAnswer").fadeOut(1000);
    })
    $("#save").click(function(){
        if(document.getElementById("editAnswer").value != ''){
            $(".editanswerform").submit();
        } else {
            document.getElementById("editAnswer").className = "Invalid";
        }
    })
});
</script>