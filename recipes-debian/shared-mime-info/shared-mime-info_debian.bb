# base recipe: meta/recipes-support/shared-mime-info/shared-mime-info_1.10.bb
# base branch: warrior
# base commit: afc016553ba1a089b766c5808d695093bda6aef6

require ${COREBASE}/meta/recipes-support/shared-mime-info/shared-mime-info.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/shared-mime-info.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-support/shared-mime-info/shared-mime-info"

SRC_URI += "file://parallelmake.patch \
	    file://install-data-hook.patch"
