# base recipe: meta-openembedded/meta-networking/recipes-connectivity/vlan/vlan_1.9.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

inherit debian-package
require recipes-debian/sources/vlan.inc

SUMMARY = "VLAN provides vconfig utility"
HOMEPAGE = "https://salsa.debian.org/debian/vlan"
SECTION = "misc"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://vconfig;md5=d52fdc11c3c3de9755865a204ff903d9"

inherit update-alternatives

do_install () {
    install -d ${D}/${base_sbindir}
    install -m 0755 ${S}/vconfig ${D}/${base_sbindir}/
}

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN} = "vconfig"
ALTERNATIVE_LINK_NAME[vconfig] = "${base_sbindir}/vconfig"
