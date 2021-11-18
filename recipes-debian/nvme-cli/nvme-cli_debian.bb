# base recipe: meta-openembedded/meta-oe/recipes-bsp/nvme-cli/nvme-cli_1.6.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "NVMe management command line interface"
AUTHOR = "Stefan Wiehler <stefan.wiehler@missinglinkelectronics.com>"
HOMEPAGE = "https://github.com/linux-nvme/nvme-cli"
SECTION = "console/utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8264535c0c4e9c6c335635c4026a8022"
DEPENDS = "util-linux"

inherit debian-package
require recipes-debian/sources/nvme-cli.inc

FILES_${PN} += "\
    ${datadir}/zsh \
"

inherit bash-completion

do_install() {
    oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
