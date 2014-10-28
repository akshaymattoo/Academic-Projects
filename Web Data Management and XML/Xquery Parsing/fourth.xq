(:Name akshay mattoo 1000995551:)
for $y in doc("auction.xml")/site/categories/category

for $x in doc("auction.xml")/site/people/person

return if($x/profile/interest/@category=$y/@id)
then (<name category="{data($y/name)}">{data($x/name)}</name> ,'&#xa;')
else()
