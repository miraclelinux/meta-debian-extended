# base recipe: meta/recipes-connectivity/iproute2/iproute2_4.19.0.bb
# base branch: warrior
# base commit: 2c7c08232e6757e9b723dc1c8440bae4408c7123

require ${COREBASE}/meta/recipes-connectivity/iproute2/iproute2.inc

inherit debian-package
require recipes-debian/sources/iproute2.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-connectivity/iproute2/iproute2:"
SRC_URI += "file://0001-Rebase-configure-cross.patch \
           file://0001-libc-compat.h-add-musl-workaround.patch \
           file://0001-ip-Remove-unneed-header.patch \
          "

# CFLAGS are computed in Makefile and reference CCOPTS
#
EXTRA_OEMAKE_append = " CCOPTS='${CFLAGS}'"

BBCLASSEXTEND = "native nativesdk"
