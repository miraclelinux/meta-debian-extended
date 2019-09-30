# base recipe: meta-debian/insserv/insserv_debian.bb
# base branch: warrior
# base commit: 3f609957cd1c373ced7ad32d21e5d688f118eba0
SUMMARY = "Boot sequence organizer using LSB init.d dependencies"
DESCRIPTION = "This utility reorders the init.d boot scripts based on \
dependencies agiven in scripts'LSB comment headers, or in override files \
included in this package or added in /etc/insserv."
# There is no known home page for insserv

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

inherit debian-package
require recipes-debian/sources/insserv.inc

#Add option -D-GNU_SOURCE: The O_NOATIME variable in insserv.c \
#is depends on __USE_GNU and __USE_GNU depends on _GNU_SOURCE
CFLAGS += " -D_GNU_SOURCE"

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

INSTDIR = "${D}"
INSTDIR_class-native = "${D}/${STAGING_DIR_NATIVE}"
do_install () {
	oe_runmake 'DESTDIR=${INSTDIR}' install

	install -m0644 ${S}/insserv.conf ${D}/${sysconfdir}/insserv.conf
	
	#Create /usr/share/bash-completion/completions folder
	install -d ${D}/${datadir}/bash-completion/completions 
	chmod 0755 ${D}/${datadir}/bash-completion/completions 

	#Install /etc/bash_completion.d/insserv
	install -m 0644 ${S}/debian/insserv.bash-completion \
			${D}/${datadir}/bash-completion/completions/insserv

	#Create /usr/share/bash-completion/completions folder
	install -d ${D}/${datadir}/${BPN}
	chmod 0755 ${D}/${datadir}/${BPN}

	#Install check-archive-initd-scripts, check-initd-order, make-testsuite, seq-changes
	# in /usr/share/insserv folder
	install -m 0644 ${S}/debian/check-archive-initd-scripts ${D}/${datadir}/${BPN}
	install -m 0644 ${S}/debian/check-initd-order           ${D}/${datadir}/${BPN}
	install -m 0644 ${S}/debian/make-testsuite              ${D}/${datadir}/${BPN}
	install -m 0644 ${S}/debian/seq-changes                 ${D}/${datadir}/${BPN}
}

FILES_${PN} += "${datadir}/bash-completion/completions/insserv"

BBCLASSEXTEND = "native"
