<div class="modalDeleteAnswer"
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
<div class="modal modalDeleteAnswer" style="position:fixed; top:30vh;  z-index: 100 !important;">
    <div class="modal-content">
        <h4>Bent u zeker?</h4>
    </div>
    <div class="modal-footer">
        <a id="closeDelete" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Nee</a>
        <a id="saveDelete" class="modal-action modal-close waves-effect waves-white green accent-3 btn-flat" style="color:white;">Ja</a>
    </div>
</div>

<script>
 $(document).ready(function(){
    $(".deleteanswer").click(function(){
        var id = this.id;
        document.getElementById("saveDelete").href="/antwoorden/delete/" + id; 
        $(".modalDeleteAnswer").fadeIn(1000);
    });
    $("#closeDelete").click(function(){
           $(".modalDeleteAnswer").fadeOut(1000);
    })
});
</script>