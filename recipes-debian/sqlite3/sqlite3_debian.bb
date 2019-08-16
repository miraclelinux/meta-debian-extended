# base recipe: meta/recipes-support/sqlite/sqlite3_3.27.2.bb
# base branch: warrior
# base commit: f7db0478d2387a4534a555b2cdb6429edf984adc

require sqlite3.inc

EXTRA_OEMAKE = "CROSS_BUILDING=yes"
