<div class="modalDeleteCategory"
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
<div class="modal modalDeleteCategory" style="position:fixed; top:30vh;  z-index: 100 !important;">
    <div class="modal-content">
        <h4>Bent u zeker?</h4>
        <p>De vragen die aan deze categorie gekoppeld zijn worden niet verwijderd.</p>
    </div>
    <div class="modal-footer">
        <a id="closeDelete" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Nee</a>
        <a id="saveDelete" class="modal-action modal-close waves-effect waves-white green btn-flat" style="color:white;">Ja</a>
    </div>
</div>

<script>
function addDeleteListener(){
    $(".deletecategory").click(function(){
        var id = this.id;
        document.getElementById("saveDelete").href="categorieën/delete/" + id; 
        $(".modalDeleteCategory").fadeIn(1000);
    });
    $("#closeDelete").click(function(){
           $(".modalDeleteCategory").fadeOut(1000);
    })
}
</script>