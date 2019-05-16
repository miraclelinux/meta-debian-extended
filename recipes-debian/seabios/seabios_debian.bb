# From meta-virtualization/recipes-extended/seabios
# rev: 279dbced29e928618ba836226542926604d7e570
# url: http://git.yoctoproject.org/cgit/cgit.cgi/meta-virtualization/tree/recipes-extended/seabios?h=warrior

DESCRIPTION = "SeaBIOS"
HOMEPAGE = "http://www.coreboot.org/SeaBIOS"
LICENSE = "LGPLv3"
SECTION = "firmware"

DEBIAN_QUILT_PATCHES = ""
inherit debian-package
require recipes-debian/sources/seabios.inc

SRC_URI += " \
    file://hostcc.patch \
    "
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504         \
                    file://COPYING.LESSER;md5=6a6a8e020838b23406c81b19c1d46df6  \
                    "

FILES_${PN} = "/usr/share/seabios"

DEPENDS = "util-linux-native file-native bison-native flex-native gettext-native acpica-native python-native"

TUNE_CCARGS = ""
EXTRA_OEMAKE += "HOSTCC='${BUILD_CC}'"
EXTRA_OEMAKE += "CC='${BUILD_CC}'"
EXTRA_OEMAKE += "LD='${BUILD_LD}'"

do_configure() {
    oe_runmake defconfig
}

do_compile() {
    unset CPP
    unset CPPFLAGS
    oe_runmake
}

do_install() {
    oe_runmake
    install -d ${D}/usr/share/seabios
    install -m 0644 out/bios.bin ${D}/usr/share/seabios/
}

BBCLASSEXTEND = "native nativesdk"