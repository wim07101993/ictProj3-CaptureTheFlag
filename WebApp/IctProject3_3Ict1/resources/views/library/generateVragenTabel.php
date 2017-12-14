<script>
    var changed = false;
    $("#search").on('change keydown paste input', function(){
        let trefwoord = $("#search").val();
        filteredVragen = vragen.filter(function(vraag){
            if(vraag["Question"].toLowerCase().indexOf(trefwoord.toLowerCase())!=-1||(vraag["Question_ID"]+"").indexOf(trefwoord)!=-1){
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
        for(vraag of filteredVragen){
            TableHtml+="<tr>";
            TableHtml+="<td>"+vraag["Question"]+"</td>";
            
            var aantalCategorieënClass = 'class="red"';
            if(vraag["Categories"].length != 0){ 
                aantalCategorieënClass = 'class="green"'
            }

            TableHtml+='<td><a class="waves-effect waves-light btn grey editcategories" id="'+vraag["Question_ID"]+'"><span '+ aantalCategorieënClass + 'style="border-radius:2px;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.14), 0 1px 5px 0 rgba(0,0,0,0.12), 0 3px 1px -2px rgba(0,0,0,0.2);">&nbsp;'+vraag["Categories"].length +'&nbsp;</span>&nbsp;&nbsp;&nbsp;Categorieën</a></td>';

            var aantalVragenClass = 'class="red"';
            
            if(vraag["Answers"].length != 0){ 
                vraag["Answers"].filter((antwoord) => {
                if(antwoord["Correct"] == 1){
                        aantalVragenClass = 'class="green"'
                    }
                });
            }
            
            TableHtml+='<td><a class="waves-effect waves-light btn grey editanwsers" href="antwoorden/'+vraag["Question_ID"]+'"><span '+ aantalVragenClass + 'style="border-radius:2px;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.14), 0 1px 5px 0 rgba(0,0,0,0.12), 0 3px 1px -2px rgba(0,0,0,0.2);">&nbsp;'+vraag["Answers"].length +'&nbsp;</span>&nbsp;&nbsp;&nbsp;Antwoorden</a></td>';
            TableHtml+=' <td><a class="waves-effect waves-light btn green editquestion" id="'+vraag["Question_ID"]+'">Bewerk</a></td>';
            TableHtml+='<td><a class="waves-effect waves-light btn red deletequestion" id="'+vraag["Question_ID"]+'">Verwijder</a></td>';
        }
        TableHtml+=' <tr class="grey lighten-3" style="border:none;"><td>';                 
        TableHtml+='<a class="btn-floating btn-large waves-effect waves-light green addquestion"><i class="material-icons">add</i></a>';
        TableHtml+='</td></tr>';
        $("#table").html(TableHtml);
        addEditListeners();
        addEditCategoryListener();
        addAddListener();
        addDeleteListener();
    }
    generateVragenTabel();
</script>