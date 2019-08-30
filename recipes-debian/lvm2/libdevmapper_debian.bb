# base recipe: https://github.com/openembedded/meta-openembedded/tree/warrior/meta-oe/recipes-support/lvm2
# base branch: warrior
# base commit: 9e4214abfabe000cc5b71b0dfc67708573b149ad

require lvm2.inc

DEPENDS += "autoconf-archive-native"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
    oe_runmake 'DESTDIR=${D}' -C libdm install
}

RRECOMMENDS_${PN}_append_class-target = " lvm2-udevrules"

BBCLASSEXTEND = "native nativesdk"
