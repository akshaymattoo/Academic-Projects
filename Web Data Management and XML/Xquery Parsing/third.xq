(:Name akshay mattoo 1000995551:)
for $x in doc("auction.xml")/site/closed_auctions/closed_auction
  let $pname := $x/buyer/@person
  group by $pname
  order by $pname
  return( (doc("auction.xml")/site/people/person[@id=$x/buyer/@person]/name , <size>{sum($x/quantity)}</size>), '&#xa;')