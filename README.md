BasicBarrels
============

BasicBarrels allows you to craft a barrel which allows you to store a large number of a single type of items.
There are a number of different types of barrels which have different storage capacities

* Iron: 64 stacks
* Gold: 1024 stacks
* Diamond: 4096 stacks

Barrels can be crafted with any log type, allowing for customization. The crafting recipe is:
EME
MLM
EME

Where:
E = Enderperl
M = Barrels "storage" material; iron, gold or diamond.
L = Log, this is the log block which gives the barrel its physical appearance.


When placed barrels are locked to their owners ensuring no other players can take items. This can be disabled via:
/barrel unlock

The barrel can be locked again via:
/barrel lock

No all items can be stored in barrels, specifically items with metadata (e.g. custom Lore text, items with damage). A blacklist feature is
also provided so storage of specific items is disabled, by default the blacklist stops valuables being stored.