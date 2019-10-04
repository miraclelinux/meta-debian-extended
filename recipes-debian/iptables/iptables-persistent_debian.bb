SUMMARY = "boot-time loader for netfilter configuration"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=efa38437dfaf00cb7e3dd14dc520ca65"

inherit debian-package
require recipes-debian/sources/iptables-persistent.inc
DEBIAN_PATCH_TYPE="quilt"
DEBIAN_QUILT_PATCHES = ""

RDEPENDS_${PN} = "iptables"

do_install() {
    oe_runmake 'DESTDIR=${D}' install

    install ${S}/plugins/* ${D}${datadir}/netfilter-persistent/plugins.d/.
}

FILES_${PN} += "${datadir}/netfilter-persistent/plugins.d/*"

inherit systemd
REQUIRED_DISTRO_FEATURES = "systemd"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "netfilter-persistent.service"
