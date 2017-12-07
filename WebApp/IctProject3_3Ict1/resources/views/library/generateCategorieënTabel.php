<script>
    var changed = false;
    $("#search").on('change keydown paste input', function(){
        let trefwoord = $("#search").val();
        filteredCategorieën = categorieën.filter(function(categorie){
            if(categorie["Name"].toLowerCase().indexOf(trefwoord.toLowerCase())!=-1||(categorie["Category_ID"]+"").indexOf(trefwoord)!=-1){
                return true;
            }
            return false;
        });
        changed=true;
    });
    setInterval(function(){
        if(changed){
            generateVragenTabel();
            changed=false;
        }
    },500);
</script>
<script>
    function generateVragenTabel(){
        var TableHtml="";
        for(categorie of filteredCategorieën){
            if(categorie["Category_ID"] != 1){
                TableHtml+="<tr>";
                TableHtml+="<td>"+categorie["Name"]+"</td>";
                TableHtml+=' <td><a class="waves-effect waves-light btn green editcategory" id="'+categorie["Category_ID"]+'">Bewerk</a></td>';
                TableHtml+='<td><a class="waves-effect waves-light btn red deletecategory" id="'+categorie["Category_ID"]+'">Verwijder</a></td>';
            }
        }
        TableHtml+=' <tr class="grey lighten-3" style="border:none;"><td>';                 
        TableHtml+='<a class="btn-floating btn-large waves-effect waves-light green addcategory"><i class="material-icons">add</i></a>';
        TableHtml+='</td></tr>';
        $("#table").html(TableHtml);
        addEditListeners();
        addAddListener();
        addDeleteListener();
    }
    generateVragenTabel();
</script>