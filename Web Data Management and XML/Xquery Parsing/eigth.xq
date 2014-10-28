(:Name akshay mattoo 1000995551:)
for $s in doc("auction.xml")/site/open_auctions/open_auction
let $x := $s[bidder/personref/@person="person3"] 
let $y := $s[bidder/personref/@person="person6"] 
return if($x/bidder/date < $y/bidder/date)
then ($x/reserve)

else if($x/bidder/date = $y/bidder/date)
then (if($x/bidder/time < $y/bidder/time)
	then ($x/reserve)
else())

else()
