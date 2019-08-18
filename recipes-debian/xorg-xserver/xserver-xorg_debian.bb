# base recipe: meta/recipes-graphics/xorg-xserver/xserver-xorg_1.20.4.bb
# base branch: warrior
# base commit: 62c9d641092dbbb1ccd6439ac415b195e39f89bd

require ${COREBASE}/meta/recipes-graphics/xorg-xserver/xserver-xorg.inc

# clear SRC_URI
SRC_URI = ""
S = ""
PE = ""
INC_PR = ""

inherit debian-package
require recipes-debian/sources/xorg-server.inc
BPN = "xorg-server"
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/xorg-xserver/xserver-xorg"

SRC_URI += "file://musl-arm-inb-outb.patch \
            file://pkgconfig.patch \
            file://0001-test-xtest-Initialize-array-with-braces.patch \
            "

# Same as debian/patches/06_use-intel-only-on-pre-gen4.diff
# file://0001-xf86pciBus.c-use-Intel-ddx-only-for-pre-gen4-hardwar.patch

# These extensions are now integrated into the server, so declare the migration
# path for in-place upgrades.

RREPLACES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RPROVIDES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RCONFLICTS_${PN} = "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
