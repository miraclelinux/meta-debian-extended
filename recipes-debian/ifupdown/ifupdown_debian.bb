# base recipe: poky/meta/recipes-core/ifupdown/ifupdown_0.8.16.bb
# base branch: warrior
# base commit: 96d783fa39151ee638209158cd6e5e71a1601477

SUMMARY = "ifupdown: basic ifup and ifdown used by initscripts"
DESCRIPTION = "High level tools to configure network interfaces \
This package provides the tools ifup and ifdown which may be used to \
configure (or, respectively, deconfigure) network interfaces, based on \
the file /etc/network/interfaces."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit debian-package
require recipes-debian/sources/ifupdown.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

FILESPATH_append = ":${COREBASE}/meta/recipes-core/ifupdown/files/"

SRC_URI += "file://defn2-c-man-don-t-rely-on-dpkg-architecture-to-set-a.patch \
	   file://inet-6-.defn-fix-inverted-checks-for-loopback.patch \
	   file://makefile.patch \
	   file://99_network \
	  "

S = "${WORKDIR}/${BPN}"

inherit update-alternatives


do_compile () {
	chmod a+rx *.pl *.sh
	oe_runmake 'CC=${CC}' "CFLAGS=${CFLAGS} -Wall -W -D'IFUPDOWN_VERSION=\"${PV}\"'"
}

do_install () {
	install -d ${D}${mandir}/man8 \
		  ${D}${mandir}/man5 \
		  ${D}${base_sbindir}

	# If volatiles are used, then we'll also need /run/network there too.
	install -d ${D}/etc/default/volatiles
	install -m 0644 ${WORKDIR}/99_network ${D}/etc/default/volatiles

	install -m 0755 ifup ${D}${base_sbindir}/
	ln ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifdown
	install -m 0644 ifup.8 ${D}${mandir}/man8
	install -m 0644 interfaces.5 ${D}${mandir}/man5
	cd ${D}${mandir}/man8 && ln -s ifup.8 ifdown.8
}

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN} = "ifup ifdown"

ALTERNATIVE_LINK_NAME[ifup] = "${base_sbindir}/ifup"
ALTERNATIVE_LINK_NAME[ifdown] = "${base_sbindir}/ifdown"
