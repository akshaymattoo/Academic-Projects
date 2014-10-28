(:Name akshay mattoo 1000995551:)
for $x in doc("auction.xml")/site/regions/*/item
order by $x/name
return ($x/name | $x/location,'&#xa;')