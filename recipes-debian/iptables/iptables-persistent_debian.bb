SUMMARY = "boot-time loader for netfilter configuration"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=efa38437dfaf00cb7e3dd14dc520ca65"

inherit debian-package
require recipes-debian/sources/iptables-persistent.inc
DEBIAN_PATCH_TYPE="quilt"
DEBIAN_QUILT_PATCHES = ""

SRC_URI += "file://busybox.patch"

RDEPENDS_${PN} = "iptables"

do_install() {
    oe_runmake 'DESTDIR=${D}' install

    install ${S}/plugins/* ${D}${datadir}/netfilter-persistent/plugins.d/.
    rm ${D}${datadir}/netfilter-persistent/plugins.d/*-ipset
    install -d -m 750 ${D}${sysconfdir}/iptables
    install -d -m 755 ${D}${sysconfdir}/default
    install -m 644 ${S}/debian/netfilter-persistent.default ${D}${sysconfdir}/default/netfilter-persistent
}

FILES_${PN} += "${datadir}/netfilter-persistent/plugins.d/*"

inherit systemd
REQUIRED_DISTRO_FEATURES = "systemd"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "netfilter-persistent.service"
