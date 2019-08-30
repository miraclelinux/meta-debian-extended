# base recipe: https://github.com/openembedded/meta-openembedded/tree/warrior/meta-oe/recipes-devtools/lshw/lshw_02.18.bb
# base branch: warrior
# base commit: 64784f7568183482d1c62536317598afac53c4b1

DESCRIPTION = "A small tool to provide detailed information on the hardware \
configuration of the machine. It can report exact memory configuration, \
firmware version, mainboard configuration, CPU version and speed, cache \
configuration, bus speed, etc. on DMI-capable or EFI systems."
SUMMARY = "Hardware lister"
HOMEPAGE = "http://ezix.org/project/wiki/HardwareLiSter"
SECTION = "console/tools"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEBIAN_UNPACK_DIR = "${WORKDIR}/lshw-${PV}"

inherit debian-package
require recipes-debian/sources/lshw.inc

COMPATIBLE_HOST = "(i.86|x86_64|arm|aarch64).*-linux"

SRC_URI += " \
    file://0003-sysfs-Fix-basename-build-with-musl.patch \
"

S = "${WORKDIR}/lshw-${PV}"

do_compile() {
    # build core only - don't ship gui
    oe_runmake -C src core
}

do_install() {
    oe_runmake install DESTDIR=${D}
}

BBCLASSEXTEND = "native"
