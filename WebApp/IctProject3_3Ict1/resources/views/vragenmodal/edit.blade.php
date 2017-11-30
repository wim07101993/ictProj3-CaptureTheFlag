<div class="modalEditQuestion"
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
<div class="modal modalEditQuestion" style="position:fixed; top:30vh; z-index: 100 !important;">
    <div class="modal-content">
        <form class="editquestionform" method="post" action="/vragen/edit">
                
        </form>
    </div>
    <div class="modal-footer">
        <a id="close" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Annuleer</a>
        <a id="save" class="modal-action modal-close waves-effect waves-white green accent-3 btn-flat" style="color:white;">Opslaan</a>
    </div>
</div>

<script>
 $(document).ready(function(){
    $(".editquestion").click(function(){
        var id = this.id;
        var vraag = vragen.filter(function(vraag){
            return vraag.Question_ID == id;
        });
        vraag=vraag[0]
        console.log(vraag);
        $(".modalEditQuestion").fadeIn(1000);
        var generateView="";
        generateView+=csrf;
            generateView+="<h4>Vraag</h4>"; 
            generateView+="<input name='id' value='"+vraag.Question_ID+"' style='display:none;'/>";
            generateView+="<input id='editQuestion' name='question' value='"+vraag.Question+"' minlength='1'/>";
        $(".editquestionform").html(generateView)
    });
    $("#close").click(function(){
           $(".modalEditQuestion").fadeOut(1000);
    })
    $("#save").click(function(){
        if(document.getElementById("editQuestion").value != ''){
            $(".editquestionform").submit();
        } else {
            document.getElementById("editQuestion").className = "Invalid";
        }
    })
});
</script>