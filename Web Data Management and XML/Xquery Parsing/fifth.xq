(:Name akshay mattoo 1000995551:)
for $y in doc("auction.xml")/site/categories/category
let $z := $y

for $x in doc("auction.xml")/site/people/person
where $x/profile/interest/@category=$y/@id
group by $z
return (<category name="{data($y/name)}">{count($x/name)}</category> ,'&#xa;')