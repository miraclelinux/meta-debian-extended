# base recipe: meta-openembedded/meta-networking/recipes-support/ifenslave/ifenslave_2.9.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

inherit debian-package
require recipes-debian/sources/ifenslave.inc

SUMMARY = "Configure network interfaces for parallel routing"
HOMEPAGE = "http://www.linuxfoundation.org/collaborate/workgroups/networking/bonding"
SECTION = "net"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=acc89812938cf9ad6b1debc37cea0253"

DEBIAN_PATCH_TYPE="quilt"

DEBIAN_UNPACK_DIR="${WORKDIR}/${PN}"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/ifenslave ${D}${sbindir}/

    install -m 0755 -D ${S}/debian/ifenslave.if-pre-up ${D}${sysconfdir}/network/if-pre-up.d/ifenslave
    install -m 0755 -D ${S}/debian/ifenslave.if-post-down ${D}${sysconfdir}/network/if-post-down.d/ifenslave
    install -m 0755 -D ${S}/debian/ifenslave.if-up ${D}${sysconfdir}/network/if-up.d/ifenslave
    install -m 0644 -D ${S}/debian/ifenslave.8 ${D}${mandir}/man8/ifenslave.8
}

FILES_${PN}-doc_remove = "${mandir}"
FILES_${PN} += "${mandir}/man8/ifenslave.8"
