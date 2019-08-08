# base recipe: meta/recipes-connectivity/nfs-utils/libnfsidmap_0.25.bb
# base branch: yocto-2.3
# base commit: 254bfb107134702d8d1e0bfbdd1b011212e8c291

SUMMARY = "NFS id mapping library"
HOMEPAGE = "http://www.citi.umich.edu/projects/nfsv4/linux/"
SECTION = "libs"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=d9c6a2a0ca6017fda7cd905ed2739b37"

inherit debian-package
require recipes-debian/sources/libnfsidmap.inc

SRC_URI += "file://fix-ac-prereq.patch \
           file://Set_nobody_user_group.patch \
           file://0001-include-sys-types.h-for-getting-u_-typedefs.patch \
          "

UPSTREAM_CHECK_URI = "http://www.citi.umich.edu/projects/nfsv4/linux/libnfsidmap/"

inherit autotools

EXTRA_OECONF = "--disable-ldap"

do_install_append () {
	install -d ${D}${sysconfdir}/
	install -m 0644 ${WORKDIR}/${BPN}-${PV}/idmapd.conf ${D}${sysconfdir}/idmapd.conf
}

