# Set dependencies for target-sdk-provides-dummy.
# Poky rev: 0e392026ffefee098a890c39bc3ca1f697bacb52

VIRTUAL-RUNTIME_base-utils-syslog ?= "busybox-syslog"
DUMMYPROVIDES =+ " ${VIRTUAL-RUNTIME_base-utils-syslog}"

VIRTUAL-RUNTIME_initscripts ?= "sysvinit"
SYSVINIT_SCRIPTS = "${VIRTUAL-RUNTIME_base-utils-hwclock} \
                    modutils-initscripts \
                    init-ifupdown \
                    ${VIRTUAL-RUNTIME_initscripts} \
                   "

DUMMYPROVIDES =+ " ${SYSVINIT_SCRIPTS}"

DUMMYPROVIDES_remove = "${@bb.utils.contains('IMAGE_INSTALL', 'busybox', '\
		busybox \
		busybox-dev \
		busybox-src \
		', '', d)}"

DUMMYPROVIDES_remove = "${@bb.utils.contains('IMAGE_INSTALL', 'coreutils', '\
		coreutils \
		coreutils-dev \
		coreutils-src \
		', '', d)}"

DUMMYPROVIDES_remove = "${@bb.utils.contains('IMAGE_INSTALL', 'bash', '\
		bash \
		bash-dev \
		bash-src \
		', '', d)}"

DUMMYPROVIDES_remove = "${@bb.utils.contains('IMAGE_INSTALL', 'perl', '\
		perl \
		perl-dev \
		perl-src \
		perl-module-re \
		perl-module-strict \
		perl-module-vars \
		perl-module-text-wrap \
		libxml-parser-perl \
		perl-module-bytes \
		perl-module-carp \
		perl-module-config \
		perl-module-constant \
		perl-module-data-dumper \
		perl-module-errno \
		perl-module-exporter \
		perl-module-file-basename \
		perl-module-file-compare \
		perl-module-file-copy \
		perl-module-file-find \
		perl-module-file-glob \
		perl-module-file-path \
		perl-module-file-stat \
		perl-module-file-temp \
		perl-module-getopt-long \
		perl-module-io-file \
		perl-module-overload \
		perl-module-overloading \
		perl-module-posix \
		perl-module-thread-queue \
		perl-module-threads \
		perl-module-warnings \
		perl-module-warnings-register \
		', '', d)}"

DUMMYPROVIDES_remove = "${@bb.utils.contains('IMAGE_INSTALL', 'pkgconfig', '\
		pkgconfig \
		pkgconfig-dev \
		pkgconfig-src \
		', '', d)}"
