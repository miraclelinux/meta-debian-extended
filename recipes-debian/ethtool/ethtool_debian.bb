# base recipe: meta/recipes-extended/ethtool/ethtool_4.19.bb
# base branch: warrior
# base commit: c8c383d958807b991e31d22f612ba2a81a3860a4

FILESPATH_append = ":${COREBASE}/meta/recipes-extended/ethtool/ethtool"
SUMMARY = "Display or change ethernet card settings"
DESCRIPTION = "A small utility for examining and tuning the settings of your ethernet-based network interfaces."
HOMEPAGE = "http://www.kernel.org/pub/software/network/ethtool/"
SECTION = "console/network"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://ethtool.c;beginline=4;endline=17;md5=c19b30548c582577fc6b443626fc1216"

inherit autotools ptest
inherit debian-package
require recipes-debian/sources/ethtool.inc

SRC_URI += " \
           file://run-ptest \
           file://avoid_parallel_tests.patch \
           "

RDEPENDS_${PN}-ptest += "make"

do_compile_ptest() {
   oe_runmake buildtest-TESTS
}

do_install_ptest () {
   cp ${B}/Makefile                 ${D}${PTEST_PATH}
   install ${B}/test-cmdline        ${D}${PTEST_PATH}
   install ${B}/test-features       ${D}${PTEST_PATH}
   install ${B}/ethtool             ${D}${PTEST_PATH}/ethtool
   sed -i 's/^Makefile/_Makefile/'  ${D}${PTEST_PATH}/Makefile
}
