(:Name akshay mattoo 1000995551:)
for $i in doc("auction.xml")/site/regions/europe/item
return ({$i/name} | {$i/description} ,'&#xa;')