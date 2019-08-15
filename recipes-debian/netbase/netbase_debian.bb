# base recipe: meta/recipes-core/netbase/netbase_5.6.bb
# base branch: warrior
# base commit: 09eda3d755def5a358bb500dfb6627a527dbb36d

SUMMARY = "Basic TCP/IP networking support"
DESCRIPTION = "This package provides the necessary infrastructure for basic TCP/IP based networking"
HOMEPAGE = "http://packages.debian.org/netbase"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=3dd6192d306f582dee7687da3d8748ab"
PE = "1"

inherit debian-package
require recipes-debian/sources/netbase.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-core/netbase/netbase"

DEBIAN_PATCH_TYPE = "nopatch"

SRC_URI += " \
           file://netbase-add-rpcbind-as-an-alias-to-sunrpc.patch"

UPSTREAM_CHECK_URI = "${DEBIAN_MIRROR}/main/n/netbase/"
do_install () {
	install -d ${D}/${mandir}/man8 ${D}${sysconfdir}
	install -m 0644 etc-rpc ${D}${sysconfdir}/rpc
	install -m 0644 etc-protocols ${D}${sysconfdir}/protocols
	install -m 0644 etc-services ${D}${sysconfdir}/services
}

CONFFILES_${PN} = "${sysconfdir}/hosts"
