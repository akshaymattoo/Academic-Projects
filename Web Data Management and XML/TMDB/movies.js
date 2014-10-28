
var table;

function initialize () {
}

function sendRequest () {
   var xhr = new XMLHttpRequest();
   var query = encodeURI(document.getElementById("form-input").value);
   xhr.open("GET", "proxy.php?method=/3/search/movie&query=" + query);
   xhr.setRequestHeader("Accept","application/json");
   xhr.onreadystatechange = function () {
       if (this.readyState == 4) {
          var json = JSON.parse(this.responseText);
          var str = JSON.stringify(json,undefined,2);
          var jsonArray = json.results;
            table = document.getElementById("myTable");
            var tableRows = table.getElementsByTagName('tr');
            var rowCount = tableRows.length;
            if(rowCount>0){
              for (var x=rowCount-1; x>=0; x--) {
                 table.deleteRow(x);
              }
            }
            if(table.style.visibility=='hidden')
              table.style.visibility='visible';
            for(var i=0;i<jsonArray.length;i++)
            {
                
                var row = table.insertRow(i);
                var cell = document.createElement('td');
                //var cell = row.insertCell(0);
                cell.innerHTML=json.results[i].title;
                var val = json.results[i].id;
                cell.setAttribute("onclick","javascript:foo("+json.results[i].id+")");
                

                row.appendChild(cell);
                //var cell1 = row.insertCell(1);
                var cell1 = document.createElement('td');
                //cell.appendChild(a);
                
                cell1.innerHTML = json.results[i].release_date;
                row.appendChild(cell1);

               
            }
            var row = table.insertRow(0);
            var hed = row.insertCell(0);
            var hed1 = row.insertCell(1);
            hed.innerHTML="Movie Name";
            hed1.innerHTML = "Release Date";
            
            //a.onclick = "foo(this)";
          }
          };
   xhr.send(null);
}

function foo(val)
{
  var box = document.getElementById("box");
  box.style.visibility="visible";
  var movie_id = val;
  var url = "/3/movie/"+movie_id;
  var cast_url = "/3/movie/"+movie_id+"/credits";

var xhr = new XMLHttpRequest();
   var query = encodeURI(document.getElementById("form-input").value);
   xhr.open("GET", "proxy.php?method="+url+"&query=" + query);
   xhr.setRequestHeader("Accept","application/json");
   xhr.onreadystatechange = function () {
       if (this.readyState == 4) {
          var json = JSON.parse(this.responseText);
          makeBox(json,box,url,cast_url);
          //var str = JSON.stringify(json,undefined,2);
          //box.innerHTML = "<pre>" + str + "</pre>";
       }
   };
   xhr.send(null);

}

function makeBox(json,box,url,cast_url)
{
box.innerHTML="";

var image_path = "http://image.tmdb.org/t/p/w185"+json.poster_path;

var genres = json.genres;
var overview = json.overview;
var title = json.title;
var str_genere="";
var cast="";
var cast_json;






var img =document.createElement("img");
img.src = image_path;



var tit = document.createElement("div");
tit.innerHTML = "TITLE : "+title;
tit.style.fontWeight = 'bold';
/*var label_title = document.createElement("label");
label_title.setAttribute("for",tit);
label_title.innerHTML = "Title";*/
//label_title.appendChild(tit);




var ovr = document.createElement("textarea");
ovr.innerHTML+="OVERVIEW:";
ovr.innerHTML+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
if(overview==null)
  ovr.innerHTML += "No Overview displayed";
else
ovr.innerHTML += overview;
ovr.style.height="200px"; 
ovr.style.width="400px"; 
ovr.style.resize='none';


if(genres.length>0){
for(var i=0;i<genres.length;i++)
 {
  
  str_genere += genres[i].name+" ,"; 
 }
 str_genere = str_genere.substring(0,str_genere.length-1);
}else
{
  str_genere="No genere displayed";
}


var gen=document.createElement("textarea");
gen.innerHTML+="GENERES:";
gen.innerHTML+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
gen.innerHTML += str_genere;
gen.style.height="80px"; 
gen.style.width="400px";
gen.style.resize='none';

var cst;
var cast_length=0;

var xhr = new XMLHttpRequest();
   var query = encodeURI(document.getElementById("form-input").value);
   xhr.open("GET", "proxy.php?method="+cast_url+"&query=" + query);
   xhr.setRequestHeader("Accept","application/json");
  
   xhr.onreadystatechange = function () {
       if (this.readyState == 4) {
          cast_json = JSON.parse(this.responseText);

          if(cast_json.cast.length>5)
            cast_length=5;
          else
              cast_length = cast_json.cast.length;

          if(cast_length>0){
            for(var i=0;i<cast_length;i++)
            {
              //console.log(cast_json.cast[i].name);
              cast += cast_json.cast[i].name+" ,";
              
            }
            cast = cast.substring(0,cast.length-1);
         }
         else{
          cast="No cast displayed";
         }
          cst = document.createElement("textarea");
          cst.innerHTML+="CAST:";
          cst.innerHTML+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
          //console.log("cast :"+cast);
          
          cst.innerHTML += cast;
          cst.style.height="80px"; 
          cst.style.width="400px";
          cst.style.resize='none';
         
          box.appendChild(img);
          box.appendChild(tit);
          box.appendChild(gen);
          box.appendChild(document.createElement("br"));
          box.appendChild(ovr);
          box.appendChild(document.createElement("br"));
          box.appendChild(cst);
       }
   };
   xhr.send(null);

}
