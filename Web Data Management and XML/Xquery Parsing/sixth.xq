(:Name akshay mattoo 1000995551:)
for $y in doc("auction.xml")/site/regions/europe/item
let $item := $y/@id
for $x in doc("auction.xml")/site/closed_auctions/closed_auction
return if($x/itemref[@item=$item])
then (<name>{data(doc("auction.xml")/site/people/person[@id=$x/buyer/@person]/name)}</name> | <itemname>{data(doc("auction.xml")/site/regions/*/item[@id=$x/itemref/@item]/name)}</itemname>,'&#xa;')
else()