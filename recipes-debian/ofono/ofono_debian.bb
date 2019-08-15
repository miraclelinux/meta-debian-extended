# base recipe: ./meta/recipes-connectivity/ofono/ofono_1.25.bb
# base branch: warrior
# base commit: 6e706b3927f0d673353fd5dc102814c14a9411b5

require ${COREBASE}/meta/recipes-connectivity/ofono/ofono.inc

inherit debian-package
require recipes-debian/sources/${BPN}.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-connectivity/ofono/ofono"
SRC_URI += "\
  file://ofono \
  file://use-python3.patch \
"
